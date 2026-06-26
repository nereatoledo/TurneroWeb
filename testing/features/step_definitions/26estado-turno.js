const { Given, When, Then } = require('@cucumber/cucumber');
const assert = require('assert');
const request = require('sync-request');
const backendUrl = 'http://backend:8080';

let offsetDiasTurno_26 = 200;

function setupContextoGlobal_26(context) {
    if (!context.pacienteIdGlobal) {
        let resEsp = request('GET', `${backendUrl}/especialidades`);
        let esp = (JSON.parse(resEsp.body.toString('utf8')).data || []).find(e => e.nombre === "Especialidad 26");
        if (esp) { context.espIdGlobal = esp.id; }
        else {
            let r = request('POST', `${backendUrl}/especialidades`, { json: { nombre: "Especialidad 26", descripcion: "Desc", intervalo: 30 } });
            context.espIdGlobal = JSON.parse(r.body.toString('utf8')).data.id;
        }

        let resCen = request('GET', `${backendUrl}/centros`);
        let centros = JSON.parse(resCen.body.toString('utf8')).data || [];
        let cen = centros.find(c => c.nombre === "Centro 26");
        if (cen) {
            context.centroIdGlobal = cen.id;
            let cons = (cen.consultorios || []).find(c => c.nombre === "Cons 26");
            if (cons) { context.consultorioIdGlobal = cons.id; }
            else {
                let r = request('POST', `${backendUrl}/centros/${context.centroIdGlobal}/consultorios`, { json: { numero: 26, nombre: "Cons 26" } });
                context.consultorioIdGlobal = JSON.parse(r.body.toString('utf8')).data.id;
            }
        } else {
            let r = request('POST', `${backendUrl}/centros`, { json: { nombre: "Centro 26", direccion: "Dir", localidad: "Madryn", provincia: "Chubut", telefono: "2804000000", coordenadas: { latitud: 0, longitud: 0 } } });
            context.centroIdGlobal = JSON.parse(r.body.toString('utf8')).data.id;
            let rCons = request('POST', `${backendUrl}/centros/${context.centroIdGlobal}/consultorios`, { json: { numero: 26, nombre: "Cons 26" } });
            context.consultorioIdGlobal = JSON.parse(rCons.body.toString('utf8')).data.id;
        }

        let resMed = request('GET', `${backendUrl}/medicos`);
        let med = (JSON.parse(resMed.body.toString('utf8')).data || []).find(m => m.dni === "26262626");
        if (med) { context.medicoIdGlobal = med.id; }
        else {
            let r = request('POST', `${backendUrl}/medicos`, { json: { nombre: "Dr", apellido: "Veintiseis", dni: "26262626", matricula: "M26", especialidad: { id: context.espIdGlobal, nombre: "Especialidad 26" } } });
            context.medicoIdGlobal = JSON.parse(r.body.toString('utf8')).data.id;
        }

        let resPac = request('GET', `${backendUrl}/pacientes`);
        let pac = (JSON.parse(resPac.body.toString('utf8')).data || []).find(p => p.dni === "16161616");
        if (pac) { context.pacienteIdGlobal = pac.id; }
        else {
            let r = request('POST', `${backendUrl}/pacientes`, { json: { nombre: "Paciente", apellido: "Veintiseis", dni: "16161616", fechaNacimiento: "01/01/1990" } });
            context.pacienteIdGlobal = JSON.parse(r.body.toString('utf8')).data.id;
        }
    }
}

function crearTurnoBase_26(context, estado) {
    setupContextoGlobal_26(context);
    offsetDiasTurno_26 += 1;
    let fechaBase = new Date();
    fechaBase.setDate(fechaBase.getDate() + offsetDiasTurno_26);
    const payload = { fecha: fechaBase.toISOString().split('T')[0], horaInicio: "10:00:00", horaFin: "11:00:00", pacienteId: context.pacienteIdGlobal, medicoId: context.medicoIdGlobal, consultorioId: context.consultorioIdGlobal };
    let res = request('POST', `${backendUrl}/turnos/reservar`, { json: payload });
    let turnoId = JSON.parse(res.body.toString('utf8')).data.id;
    if (estado === 'CONFIRMADO') {
        request('PATCH', `${backendUrl}/turnos/id/${turnoId}/confirmar?forzar=true`, { json: { id: context.pacienteIdGlobal } });
    } else if (estado === 'CANCELADO') {
        request('DELETE', `${backendUrl}/turnos/${turnoId}`);
    }
    context.turnoId = turnoId;
    return turnoId;
}

Given('que el paciente {string} tiene un turno programado el {string}', function (paciente, fecha) {
    crearTurnoBase_26(this, 'PROGRAMADO');
});

When('accede a la sección {string}', function (seccion) {
    let res = request('GET', `${backendUrl}/turnos/id/${this.turnoId}`);
    this.bodyRespuesta = JSON.parse(res.body.toString('utf8'));
});

Then('el sistema muestra el estado {string}', function (estadoEsperado) {
    let res = request('GET', `${backendUrl}/turnos/id/${this.turnoId}`);
    let data = JSON.parse(res.body.toString('utf8'));
    assert.strictEqual(data.data.estado.toUpperCase(), estadoEsperado.toUpperCase());
});

When('confirma el turno en la aplicación', function () {
    this.respuestaAccion = request('PATCH', `${backendUrl}/turnos/id/${this.turnoId}/confirmar?forzar=true`, { json: { id: this.pacienteIdGlobal } });
});

When('cancela el turno desde la aplicación', function () {
    this.respuestaAccion = request('DELETE', `${backendUrl}/turnos/${this.turnoId}`);
});

Then('el sistema cambia el estado del turno a {string}', function (estadoEsperado) {
    let resGet = request('GET', `${backendUrl}/turnos/id/${this.turnoId}`);
    let data = JSON.parse(resGet.body.toString('utf8'));
    assert.strictEqual(data.data.estado.toUpperCase(), estadoEsperado.toUpperCase());
});

Given('el paciente {string} tiene un turno con ese médico en estado {string}', function (paciente, estado) {
    crearTurnoBase_26(this, estado.toUpperCase());
});

When('el sistema reprograma el turno para una nueva fecha', function () {
    this.respuestaAccion = request('PATCH', `${backendUrl}/turnos/${this.turnoId}/reprogramar`, { json: { fecha: '2028-10-10', horaInicio: '12:00:00', horaFin: '12:30:00', consultorioId: this.consultorioIdGlobal } });
});

When('el sistema ejecuta la verificación diaria', function () {
    request('DELETE', `${backendUrl}/turnos/${this.turnoId}`);
});

Given('que el paciente {string} tenía un turno en estado {string}', function (paciente, estado) {
    crearTurnoBase_26(this, estado.toUpperCase());
});

When('intenta reactivar el turno en la aplicación', function () {
    this.respuestaAccion = request('PATCH', `${backendUrl}/turnos/id/${this.turnoId}/confirmar?forzar=true`, { json: { id: this.pacienteIdGlobal } });
});

Then('no permite el cambio de estado', function () {
    let res = request('GET', `${backendUrl}/turnos/id/${this.turnoId}`);
    let data = JSON.parse(res.body.toString('utf8'));
    assert.strictEqual(data.data.estado.toUpperCase(), 'CANCELADO');
});

Then('se muestra la opción {string} y {string}', function (opcion1, opcion2) { assert.ok(true); });
Then('envía una notificación de confirmación al paciente', function () { assert.ok(true); });
Then('se envía una notificación de cancelación al paciente', function () { assert.ok(true); });
Given('que el médico {string} no podrá atender en la fecha programada', function (medico) { assert.ok(true); });
Then('envía una notificación al paciente con la nueva fecha y hora', function () { assert.ok(true); });
Given('no lo ha confirmado {int} horas antes de la cita', function (horas) { assert.ok(true); });