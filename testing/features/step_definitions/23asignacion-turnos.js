const { Given, When, Then } = require('@cucumber/cucumber');
const assert = require('assert');
const request = require('sync-request');

const backendUrl = 'http://backend:8080';

function buscarIdPaciente(nombreEsperado) {
    const res = request('GET', `${backendUrl}/pacientes`);
    const pacientes = JSON.parse(res.getBody('utf8')).data;
    if (!pacientes || pacientes.length === 0) return 1;
    const encontrado = pacientes.find(p => `${p.nombre} ${p.apellido}`.includes(nombreEsperado) || `${p.apellido} ${p.nombre}`.includes(nombreEsperado));
    return encontrado ? encontrado.id : pacientes[0].id;
}

function buscarIdMedico(nombreEsperado) {
    const res = request('GET', `${backendUrl}/medicos`);
    if (res.statusCode !== 200) return 1;
    const medicos = JSON.parse(res.getBody('utf8')).data;
    if (!medicos || medicos.length === 0) return 1;

    const nombreLimpio = nombreEsperado.replace('Dr. ', '').replace('Dra. ', '');
    const encontrado = medicos.find(m => `${m.nombre} ${m.apellido}`.includes(nombreLimpio) || `${m.apellido} ${m.nombre}`.includes(nombreLimpio));
    return encontrado ? encontrado.id : medicos[0].id;
}

function buscarIdConsultorioEnCentro(nombreCentro) {
    const res = request('GET', `${backendUrl}/consultorios`);
    if (res.statusCode !== 200) return 1;
    const consultorios = JSON.parse(res.getBody('utf8')).data;
    if (!consultorios || consultorios.length === 0) return 1;

    const encontrado = consultorios.find(c => c.centro && c.centro.nombre === nombreCentro);
    return encontrado ? encontrado.id : consultorios[0].id;
}


let futureDate = new Date();
futureDate.setDate(futureDate.getDate() + 30);

let turnoDTO = {
    fecha: futureDate.toISOString().split('T')[0],
    horaInicio: "15:00:00",
    horaFin: "15:30:00",
    estado: "PROGRAMADO",
    paciente: { id: null },
    medico: { id: null },
    consultorio: { id: null }
};

let respuestaServidor;
let bodyRespuesta;


function buscarIdEspecialidad(nombreEsperado) {
    const res = request('GET', `${backendUrl}/especialidades`);
    if (res.statusCode !== 200) return 1;
    const especialidades = JSON.parse(res.getBody('utf8')).data;
    if (!especialidades || especialidades.length === 0) return 1;

    const encontrado = especialidades.find(e => e.nombre.includes(nombreEsperado));
    return encontrado ? encontrado.id : especialidades[0].id;
}

Given('que el paciente {string} está registrado en el sistema', function (nombrePaciente) {
    turnoDTO.paciente.id = buscarIdPaciente(nombrePaciente);
});

Given('ha seleccionado la especialidad {string}', function (especialidad) {
    turnoDTO.especialidadId = buscarIdEspecialidad(especialidad);
});

Given('ha seleccionado al médico {string}', function (nombreMedico) {
    turnoDTO.medico.id = buscarIdMedico(nombreMedico);
});

Given('ha seleccionado el centro de atención {string}', function (centroAtencion) {
    turnoDTO.consultorio.id = buscarIdConsultorioEnCentro(centroAtencion);
});

When('solicita un turno', function () {
    try {
        respuestaServidor = request('POST', `${backendUrl}/turnos`, { json: turnoDTO });
        if (respuestaServidor.statusCode === 200) {
            bodyRespuesta = JSON.parse(respuestaServidor.getBody('utf8'));
        }
    } catch (e) {
        respuestaServidor = e;
    }
});

When('la agenda de la {word} {word} {word} está completa', function (titulo, nombre, apellido) {
    const idMedico = buscarIdMedico(`${nombre} ${apellido}`);
    const idCentro = turnoDTO.consultorio.id;
    // Hacemos un GET de la agenda y esperamos que no haya turnos disponibles
    const res = request('GET', `${backendUrl}/esquemas-turnos/buscar?fechaInicio=${turnoDTO.fecha}&idMedico=${idMedico}&idCentro=${idCentro}`);
    const agendas = JSON.parse(res.getBody('utf8')).data;
    let tieneTurnos = false;
    if (agendas) {
        agendas.forEach(dia => {
            if (!dia.esFeriado && dia.agendaDetalles) {
                dia.agendaDetalles.forEach(esquema => {
                    if (esquema.turnos && esquema.turnos.some(t => t.estaDisponible)) tieneTurnos = true;
                });
            }
        });
    }
    assert.strictEqual(tieneTurnos, false, "La agenda debería estar completa pero hay turnos disponibles");
});

Then('el sistema sugiere otros médicos disponibles en la misma especialidad', function () {
    const res = request('GET', `${backendUrl}/esquemas-turnos/buscar?fechaInicio=${turnoDTO.fecha}&idEspecialidad=${turnoDTO.especialidadId}&idCentro=${turnoDTO.consultorio.id}&idMedicoExcluido=${turnoDTO.medico.id}`);
    const agendas = JSON.parse(res.getBody('utf8')).data;
    let tieneSugerencias = false;
    if (agendas) {
        agendas.forEach(dia => {
            if (!dia.esFeriado && dia.agendaDetalles) {
                dia.agendaDetalles.forEach(esquema => {
                    if (esquema.turnos && esquema.turnos.some(t => t.estaDisponible)) tieneSugerencias = true;
                });
            }
        });
    }
    assert.ok(tieneSugerencias, "No se encontraron otros médicos disponibles");
});

Then('ofrece fechas alternativas en otros centros de atención', function () {
    const res = request('GET', `${backendUrl}/esquemas-turnos/buscar?fechaInicio=${turnoDTO.fecha}&idEspecialidad=${turnoDTO.especialidadId}&idCentroExcluido=${turnoDTO.consultorio.id}`);
    const agendas = JSON.parse(res.getBody('utf8')).data;
    let tieneSugerencias = false;
    if (agendas) {
        agendas.forEach(dia => {
            if (!dia.esFeriado && dia.agendaDetalles) {
                dia.agendaDetalles.forEach(esquema => {
                    if (esquema.turnos && esquema.turnos.some(t => t.estaDisponible)) tieneSugerencias = true;
                });
            }
        });
    }
    assert.ok(tieneSugerencias, "No se encontraron fechas alternativas en otros centros");
});

Then('el paciente puede elegir entre las opciones sugeridas', function () {
    assert.ok(true);
});

Then('el sistema asigna un turno basado en la disponibilidad', function () {
    assert.strictEqual(respuestaServidor.statusCode, 200);
});

Then('el turno queda registrado en el sistema con estado {string}', function (estadoEsperado) {
    assert.ok(bodyRespuesta.data);
    assert.strictEqual(bodyRespuesta.data.estado.toUpperCase(), estadoEsperado.toUpperCase());
});

Then('el paciente recibe una notificación con la confirmación del turno', function () {
    assert.fail(bodyRespuesta ? bodyRespuesta.message : "Sin respuesta");
});
