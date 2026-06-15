const { Given, When, Then } = require('@cucumber/cucumber');
const assert = require('assert');
const request = require('sync-request');

const backendUrl = 'http://backend:8080';

// ======================================================================
// 1. HELPERS: Funciones puras
// ======================================================================

function obtenerFechasSemanaLaboral() {
    let fecha = new Date();
    let diasParaLunes = (1 - fecha.getDay() + 7) % 7;
    if (diasParaLunes === 0) diasParaLunes = 7;

    let inicio = new Date(fecha);
    inicio.setDate(fecha.getDate() + diasParaLunes);

    let fin = new Date(inicio);
    fin.setDate(inicio.getDate() + 4);

    return {
        inicio: inicio.toISOString().split('T')[0],
        fin: fin.toISOString().split('T')[0]
    };
}

function buscarIdConsultorio(nombreEsperado) {
    if (agendaDTO.idMedico) {
        return resolverIdConsultorio(nombreEsperado, agendaDTO.idMedico);
    }
    const res = request('GET', `${backendUrl}/consultorios`);
    const consultorios = JSON.parse(res.getBody('utf8')).data;
    const encontrado = consultorios.find(c => c.nombre === nombreEsperado);
    return encontrado ? encontrado.id : 1;
}

function resolverIdConsultorio(nombreConsultorio, idMedico) {
    if (!nombreConsultorio) return 1;
    const resCentros = request('GET', `${backendUrl}/centros`);
    const centros = JSON.parse(resCentros.getBody('utf8')).data;
    const resMedicos = request('GET', `${backendUrl}/centros/medicos`);
    const centrosMedicos = JSON.parse(resMedicos.getBody('utf8')).data;
    const centrosDelMedico = centrosMedicos
        .filter(c => c.medicos.some(m => m.id === idMedico))
        .map(c => c.centro_de_atencion);
    let mejorConsultorio = null;
    for (let centro of centros) {
        if (centrosDelMedico.includes(centro.nombre)) {
            let cons = centro.consultorios.find(c => c.nombre === nombreConsultorio);
            if (cons) {
                mejorConsultorio = cons;
                if (centro.nombre !== "Trelew Salud") {
                    return cons.id;
                }
            }
        }
    }
    if (mejorConsultorio) return mejorConsultorio.id;
    const resAll = request('GET', `${backendUrl}/consultorios`);
    const allCons = JSON.parse(resAll.getBody('utf8')).data;
    const encontrado = allCons.find(c => c.nombre === nombreConsultorio);
    return encontrado ? encontrado.id : 1;
}

function buscarIdMedico(nombreEsperado) {
    const res = request('GET', `${backendUrl}/centros/medicos`);
    const centrosData = JSON.parse(res.getBody('utf8')).data;

    for (let centro of centrosData) {
        let med = centro.medicos.find(m => `${m.nombre} ${m.apellido}`.includes(nombreEsperado));
        if (med) return med.id;
    }
    return 1;
}

function enviarPostAgenda(dto) {
    try {
        return request('POST', `${backendUrl}/esquemas-turnos`, { json: dto });
    } catch (error) {
        return error;
    }
}

// ======================================================================
// 2. ESTADO DEL TEST
// ======================================================================

let agendaDTO = {
    nombre: "Agenda Generada Automáticamente",
    descripcion: "Prueba Cucumber",
    horaInicio: "", horaFin: "", fechaInicio: "", fechaFin: "",
    idConsultorio: null, idMedico: null,
    feriados: []
};

let respuestaServidor;
let accionEspecial = 'CREAR';
let nombreConsultorioActual = null;

// ======================================================================
// 3. STEP DEFINITIONS
// ======================================================================

// --- Bloques para Creación Exitosa ---

Given('que el administrador configura la agenda del {string}', function (nombreConsultorio) {
    agendaDTO.feriados = [];
    agendaDTO.idMedico = null;
    accionEspecial = 'CREAR';
    nombreConsultorioActual = nombreConsultorio;
    agendaDTO.idConsultorio = buscarIdConsultorio(nombreConsultorio);
});

Given('define el horario de atención de {string} a {string} de {word} a {word}', function (horaInicio, horaFin, diaDesde, diaHasta) {
    let fechas = obtenerFechasSemanaLaboral();
    agendaDTO.horaInicio = horaInicio;
    agendaDTO.horaFin = horaFin;
    agendaDTO.fechaInicio = fechas.inicio;
    agendaDTO.fechaFin = fechas.fin;
});

Given('asigna al Dr. {string} con especialidad {string}', function (nombreMedico, especialidad) {
    agendaDTO.idMedico = buscarIdMedico(nombreMedico);
    if (nombreConsultorioActual) {
        agendaDTO.idConsultorio = resolverIdConsultorio(nombreConsultorioActual, agendaDTO.idMedico);
    }
});



Given('define un horario de atención de {string} a {string} para el Dr. {string}', function (horaInicio, horaFin, nombreMedico) {
});

Given('posteriormente intenta asignar al Dr. {string} de {string} a {string} en el mismo consultorio', function (nombreMedico, horaInicio, horaFin) {
    let fechas = obtenerFechasSemanaLaboral();
    agendaDTO.horaInicio = horaInicio;
    agendaDTO.horaFin = horaFin;
    agendaDTO.fechaInicio = fechas.inicio;
    agendaDTO.fechaFin = fechas.fin;
    agendaDTO.idMedico = buscarIdMedico(nombreMedico);
    if (nombreConsultorioActual) {
        agendaDTO.idConsultorio = resolverIdConsultorio(nombreConsultorioActual, agendaDTO.idMedico);
    }
});

Given('que el Dr. {string} está asignado al {string} de {string} a {string}', function (nombreMedico, nombreConsultorio, horaInicio, horaFin) {
    let fechas = obtenerFechasSemanaLaboral();
    agendaDTO.horaInicio = horaInicio;
    agendaDTO.horaFin = horaFin;
    agendaDTO.fechaInicio = fechas.inicio;
    agendaDTO.fechaFin = fechas.fin;
    agendaDTO.idMedico = buscarIdMedico(nombreMedico);
    agendaDTO.idConsultorio = resolverIdConsultorio(nombreConsultorio, agendaDTO.idMedico);
});

When('el administrador intenta asignarlo al {string} en el mismo horario', function (nombreConsultorio) {
    agendaDTO.idConsultorio = resolverIdConsultorio(nombreConsultorio, agendaDTO.idMedico);
    respuestaServidor = enviarPostAgenda(agendaDTO);
});



When('agrega el {string} como día feriado', function (fechaFeriado) {
    const meses = {
        "enero": "01", "febrero": "02", "marzo": "03", "abril": "04",
        "mayo": "05", "junio": "06", "julio": "07", "agosto": "08",
        "septiembre": "09", "octubre": "10", "noviembre": "11", "diciembre": "12"
    };
    const partes = fechaFeriado.split(" ");
    const dia = partes[0].padStart(2, '0');
    const mes = meses[partes[2].toLowerCase()];
    const anio = new Date().getFullYear();

    agendaDTO.feriados = [`${anio}-${mes}-${dia}`];

    // Se envía tal cual. agendaDTO.idMedico está en null gracias al reseteo del primer paso.
    respuestaServidor = enviarPostAgenda(agendaDTO);
});



Given('que el Dr. {string} tiene turnos asignados en el {string}', function (nombreMedico, nombreConsultorio) {
    agendaDTO.idMedico = buscarIdMedico(nombreMedico);
    agendaDTO.idConsultorio = buscarIdConsultorio(nombreConsultorio);
    accionEspecial = 'CANCELAR';
});

Given('el administrador elimina su disponibilidad por motivos personales', function () {
    accionEspecial = 'CANCELAR';
});



When('guarda la configuración', function () {
    if (accionEspecial === 'CANCELAR') {
        respuestaServidor = request('DELETE', `${backendUrl}/esquemas-turnos/medico/${agendaDTO.idMedico}/consultorio/${agendaDTO.idConsultorio}`);
    } else {
        respuestaServidor = enviarPostAgenda(agendaDTO);
    }
});

Then('el sistema confirma la creación de la agenda con código {int}', function (codigoEsperado) {
    if (respuestaServidor.statusCode !== codigoEsperado) {
        console.log("Error del servidor: ", respuestaServidor.body.toString('utf8'));
    }
    assert.strictEqual(respuestaServidor.statusCode, codigoEsperado);
});

Then('el sistema muestra un mensaje de error {string}', function (mensajeEsperado) {
    const cuerpoCrudo = respuestaServidor.body.toString('utf8');
    const cuerpoRespuesta = JSON.parse(cuerpoCrudo);
    assert.strictEqual(cuerpoRespuesta.message, mensajeEsperado);
});

Then('devuelve un código de estado {int}', function (codigoEsperado) {
    assert.strictEqual(respuestaServidor.statusCode, codigoEsperado);
});

Then('el sistema guarda la configuración correctamente con código {int}', function (codigoEsperado) {
    if (respuestaServidor.statusCode !== codigoEsperado) {
        console.log("Error del servidor: ", respuestaServidor.body.toString('utf8'));
    }
    assert.strictEqual(respuestaServidor.statusCode, codigoEsperado);
});

Then('el sistema notifica a los pacientes afectados', function () {
    return 'pending';
});

Then('ofrece opciones de reprogramación', function () {
    return 'pending';
});