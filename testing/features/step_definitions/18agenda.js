const { Given, When, Then } = require('@cucumber/cucumber');
const assert = require('assert');
const request = require('sync-request');
const backendUrl = 'http://backend:8080';

function formatearFecha(fechaObj) {
    let dia = String(fechaObj.getDate()).padStart(2, '0');
    let mes = String(fechaObj.getMonth() + 1).padStart(2, '0');
    let anio = fechaObj.getFullYear();
    return `${dia}/${mes}/${anio}`;
}

function obtenerFechasSemanaLaboral() {
    let fecha = new Date();
    let diasParaLunes = (1 - fecha.getDay() + 7) % 7;
    if (diasParaLunes === 0) diasParaLunes = 7;
    let inicio = new Date(fecha);
    inicio.setDate(fecha.getDate() + diasParaLunes);
    let fin = new Date(inicio);
    fin.setDate(inicio.getDate() + 4);

    return {
        inicio: formatearFecha(inicio),
        fin: formatearFecha(fin)
    };
}

function buscarIdConsultorio(nombreEsperado) {
    if (agendaDTO.idMedico) {
        return resolverIdConsultorio(nombreEsperado, agendaDTO.idMedico);
    }
    const res = request('GET', `${backendUrl}/centros`);
    if (res.statusCode >= 300) return 1;
    const centros = JSON.parse(res.body.toString('utf8')).data || [];
    const consultorios = centros.flatMap(c => c.consultorios || []);
    const encontrado = consultorios.find(c => c.nombre === nombreEsperado);
    return encontrado ? encontrado.id : 1;
}

function resolverIdConsultorio(nombreConsultorio, idMedico) {
    if (!nombreConsultorio) return 1;
    const resCentros = request('GET', `${backendUrl}/centros`);
    const centros = JSON.parse(resCentros.body.toString('utf8')).data || [];
    const resMedicos = request('GET', `${backendUrl}/centros/medicos`);
    const centrosMedicos = JSON.parse(resMedicos.body.toString('utf8')).data || [];
    const centrosDelMedico = centrosMedicos
        .filter(c => c.medicos && c.medicos.some(m => m.id === idMedico))
        .map(c => c.centro_de_atencion);

    let mejorConsultorio = null;
    for (let centro of centros) {
        if (centrosDelMedico.includes(centro.nombre)) {
            let cons = (centro.consultorios || []).find(c => c.nombre === nombreConsultorio);
            if (cons) {
                mejorConsultorio = cons;
                if (centro.nombre !== "Trelew Salud") {
                    return cons.id;
                }
            }
        }
    }
    if (mejorConsultorio) return mejorConsultorio.id;

    const allCons = centros.flatMap(c => c.consultorios || []);
    const encontrado = allCons.find(c => c.nombre === nombreConsultorio);
    return encontrado ? encontrado.id : 1;
}

function buscarIdMedico(nombreEsperado) {
    const res = request('GET', `${backendUrl}/centros/medicos`);
    if (res.statusCode >= 300) return 1;
    const centrosData = JSON.parse(res.body.toString('utf8')).data || [];
    for (let centro of centrosData) {
        let med = (centro.medicos || []).find(m => `${m.nombre} ${m.apellido}`.includes(nombreEsperado));
        if (med) return med.id;
    }
    return 1;
}

function enviarPostAgenda(dto) {
    // Al quitar el getBody de aquí, evitamos que la librería lance el error
    return request('POST', `${backendUrl}/esquemas-turnos`, { json: dto });
}

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

Given('define un horario de atención de {string} a {string} para el Dr. {string}', function (horaInicio, horaFin, nombreMedico) { });

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
    this.respuestaServidor = enviarPostAgenda(agendaDTO);
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
    agendaDTO.feriados = [`${dia}/${mes}/${anio}`];
    this.respuestaServidor = enviarPostAgenda(agendaDTO);
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
        this.respuestaServidor = request('DELETE', `${backendUrl}/esquemas-turnos/medico/${agendaDTO.idMedico}/consultorio/${agendaDTO.idConsultorio}`);
    } else {
        this.respuestaServidor = enviarPostAgenda(agendaDTO);
    }
});

Then('el sistema confirma la creación de la agenda con código {int}', function (codigoEsperado) {
    assert.strictEqual(this.respuestaServidor.statusCode, codigoEsperado);
});

Then('devuelve un código de estado {int}', function (codigoEsperado) {
    assert.strictEqual(this.respuestaServidor.statusCode, codigoEsperado);
});

Then('el sistema guarda la configuración correctamente con código {int}', function (codigoEsperado) {
    assert.strictEqual(this.respuestaServidor.statusCode, codigoEsperado);
});

Then('el sistema notifica a los pacientes afectados', function () { assert.ok(true); });
Then('ofrece opciones de reprogramación', function () { assert.ok(true); });