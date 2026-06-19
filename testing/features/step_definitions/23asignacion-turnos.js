const { Given, When, Then } = require('@cucumber/cucumber');
const assert = require('assert');
const request = require('sync-request');

const backendUrl = 'http://backend:8080';

function buscarIdPaciente(nombreEsperado) {
    const res = request('GET', `${backendUrl}/pacientes`);
    const pacientes = JSON.parse(res.getBody('utf8')).data || [];
    if (pacientes.length === 0) return 1;
    const encontrado = pacientes.find(p => `${p.nombre} ${p.apellido}`.includes(nombreEsperado) || `${p.apellido} ${p.nombre}`.includes(nombreEsperado));
    return encontrado ? encontrado.id : pacientes[0].id;
}

function buscarIdMedico(nombreEsperado) {
    const res = request('GET', `${backendUrl}/medicos`);
    const medicos = JSON.parse(res.getBody('utf8')).data || [];
    if (medicos.length === 0) return 1;
    const nombreLimpio = nombreEsperado.replace('Dr. ', '').replace('Dra. ', '');
    const encontrado = medicos.find(m => `${m.nombre} ${m.apellido}`.includes(nombreLimpio) || `${m.apellido} ${m.nombre}`.includes(nombreLimpio));
    return encontrado ? encontrado.id : medicos[0].id;
}

function buscarIdConsultorioEnCentro(nombreCentro) {
    const res = request('GET', `${backendUrl}/centros`);
    const centros = JSON.parse(res.getBody('utf8')).data || [];
    if (centros.length === 0) return 1;
    const centro = centros.find(c => c.nombre === nombreCentro);
    if (centro && centro.consultorios && centro.consultorios.length > 0) {
        return centro.consultorios[0].id;
    }
    const allConsultorios = centros.flatMap(c => c.consultorios || []);
    return allConsultorios.length > 0 ? allConsultorios[0].id : 1;
}

function buscarIdEspecialidad(nombreEsperado) {
    const res = request('GET', `${backendUrl}/especialidades`);
    const especialidades = JSON.parse(res.getBody('utf8')).data || [];
    if (especialidades.length === 0) return 1;
    const encontrado = especialidades.find(e => e.nombre.includes(nombreEsperado));
    return encontrado ? encontrado.id : especialidades[0].id;
}

function obtenerFechaValidaAgenda() {
    let fecha = new Date();
    let diasParaLunes = (1 - fecha.getDay() + 7) % 7;
    if (diasParaLunes === 0) diasParaLunes = 7;
    fecha.setDate(fecha.getDate() + diasParaLunes + 1);
    return fecha.toISOString().split('T')[0];
}

let horaRandom = Math.floor(Math.random() * (15 - 10 + 1) + 10);
let minRandom = "00";
let minFin = "30";
let horaFin = horaRandom;

let turnoDTO = {
    fecha: obtenerFechaValidaAgenda(),
    horaInicio: `${horaRandom.toString().padStart(2, '0')}:${minRandom}:00`,
    horaFin: `${horaFin.toString().padStart(2, '0')}:${minFin}:00`,
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
    assert.ok([200, 201].includes(respuestaServidor.statusCode), `El servidor devolvió status ${respuestaServidor.statusCode} en lugar de 200/201. Error: ${respuestaServidor.getBody('utf8')}`);
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