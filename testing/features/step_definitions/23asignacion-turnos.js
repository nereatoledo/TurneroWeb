const { Given, When, Then } = require('@cucumber/cucumber');
const assert = require('assert');
const request = require('sync-request');

const backendUrl = 'http://backend:8080';

// Functions from the file...
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

function buscarIdEspecialidad(nombreEsperado) {
    const res = request('GET', `${backendUrl}/especialidades`);
    if (res.statusCode !== 200) return 1;
    const especialidades = JSON.parse(res.getBody('utf8')).data;
    if (!especialidades || especialidades.length === 0) return 1;

    const encontrado = especialidades.find(e => e.nombre.includes(nombreEsperado));
    return encontrado ? encontrado.id : especialidades[0].id;
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
let turnoId;

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
        respuestaServidor = request('POST', `${backendUrl}/turnos`, { json: turnoDTO, throw: false });
        if (respuestaServidor.statusCode === 200 || respuestaServidor.statusCode === 201) {
            bodyRespuesta = JSON.parse(respuestaServidor.getBody('utf8'));
            if (bodyRespuesta.data) turnoId = bodyRespuesta.data.id;
        }
    } catch (e) {
        respuestaServidor = e;
    }
});

When('la agenda de la {word} {word} {word} está completa', function (titulo, nombre, apellido) {
    return 'pending';
});

Then('el sistema sugiere otros médicos disponibles en la misma especialidad', function () {
    return 'pending';
});

Then('ofrece fechas alternativas en otros centros de atención', function () {
    return 'pending';
});

Then('el paciente puede elegir entre las opciones sugeridas', function () {
    return 'pending';
});

Then('el sistema asigna un turno basado en la disponibilidad', function () {
    assert.strictEqual(respuestaServidor.statusCode, 200);
});

Then('el turno queda registrado en el sistema con estado {string}', function (estadoEsperado) {
    assert.ok(bodyRespuesta.data);
    assert.strictEqual(bodyRespuesta.data.estado.toUpperCase(), estadoEsperado.toUpperCase());
});

Then('el paciente recibe una notificación con la confirmación del turno', function () {
    return 'pending';
});



Given('que el paciente {string} tiene un turno en estado {string}', function (paciente, estado) {
    turnoDTO.paciente.id = buscarIdPaciente(paciente);
    turnoDTO.estado = estado.toUpperCase();

    turnoDTO.medico.id = buscarIdMedico("Juan Perez");
    turnoDTO.consultorio.id = buscarIdConsultorioEnCentro("Trelew Salud");

    try {
        let res = request('POST', `${backendUrl}/turnos`, { json: turnoDTO, throw: false });
        let body = JSON.parse(res.getBody('utf8'));
        if (body.data && body.data.id) turnoId = body.data.id;
    } catch (e) { }

    if (!turnoId) turnoId = 1;
});

When('accede al sistema y confirma el turno', function () {
    try {
        respuestaServidor = request('PUT', `${backendUrl}/turnos/${turnoId}/estado?estado=CONFIRMADO`, { throw: false });
        if (respuestaServidor.statusCode === 200) {
            bodyRespuesta = JSON.parse(respuestaServidor.getBody('utf8'));
        }
    } catch (e) { }
});

Then('el turno cambia de estado a {string}', function (estadoEsperado) {
    return 'pending';
});

Then('el paciente recibe una notificación de confirmación', function () {
    return 'pending';
});

Given('falta más de {int} horas para la fecha del turno', function (horas) {
    return 'pending';
});

Given('faltan menos de {int} horas para la fecha del turno', function (horas) {
    return 'pending';
});

When('el paciente cancela el turno', function () {
    try {
        respuestaServidor = request('PUT', `${backendUrl}/turnos/${turnoId}/estado?estado=CANCELADO`, { throw: false });
    } catch (e) { }
});

Then('el sistema libera el espacio para otro paciente', function () {
    return 'pending';
});

Then('el sistema registra la cancelación tardía', function () {
    return 'pending';
});

Given('que el paciente {string} tiene un turno con el médico {string}', function (paciente, medico) {
    return 'pending';
});

Given('el médico ha cancelado su agenda para ese día', function () {
    return 'pending';
});

When('el sistema detecta la cancelación', function () {
    return 'pending';
});

Then('se notifica al paciente', function () {
    return 'pending';
});

Then('el sistema sugiere fechas alternativas con el mismo médico o con otro profesional de la misma especialidad', function () {
    return 'pending';
});

When('no hay disponibilidad en esa especialidad en el centro seleccionado', function () {
    return 'pending';
});

Then('el sistema sugiere otros centros donde haya disponibilidad', function () {
    return 'pending';
});

