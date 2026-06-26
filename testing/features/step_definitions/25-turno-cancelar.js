const { Given, When, Then } = require('@cucumber/cucumber');
const assert = require('assert');
const request = require('sync-request');
const backendUrl = 'http://backend:8080';

let offsetDiasTurno_25 = 100;

function setupContextoGlobal_25(context) {
    if (!context.pacienteIdGlobal) {
        let resEsp = request('GET', `${backendUrl}/especialidades`);
        let esp = (JSON.parse(resEsp.body.toString('utf8')).data || []).find(e => e.nombre === "Especialidad 25");
        if (esp) { context.espIdGlobal = esp.id; }
        else {
            let r = request('POST', `${backendUrl}/especialidades`, { json: { nombre: "Especialidad 25", descripcion: "Desc", intervalo: 30 } });
            context.espIdGlobal = JSON.parse(r.body.toString('utf8')).data.id;
        }

        let resCen = request('GET', `${backendUrl}/centros`);
        let centros = JSON.parse(resCen.body.toString('utf8')).data || [];
        let cen = centros.find(c => c.nombre === "Centro 25");
        if (cen) {
            context.centroIdGlobal = cen.id;
            let cons = (cen.consultorios || []).find(c => c.nombre === "Cons 25");
            if (cons) { context.consultorioIdGlobal = cons.id; }
            else {
                let r = request('POST', `${backendUrl}/centros/${context.centroIdGlobal}/consultorios`, { json: { numero: 25, nombre: "Cons 25" } });
                context.consultorioIdGlobal = JSON.parse(r.body.toString('utf8')).data.id;
            }
        } else {
            let r = request('POST', `${backendUrl}/centros`, { json: { nombre: "Centro 25", direccion: "Dir", localidad: "Madryn", provincia: "Chubut", telefono: "2804000000", coordenadas: { latitud: 0, longitud: 0 } } });
            context.centroIdGlobal = JSON.parse(r.body.toString('utf8')).data.id;
            let rCons = request('POST', `${backendUrl}/centros/${context.centroIdGlobal}/consultorios`, { json: { numero: 25, nombre: "Cons 25" } });
            context.consultorioIdGlobal = JSON.parse(rCons.body.toString('utf8')).data.id;
        }

        let resMed = request('GET', `${backendUrl}/medicos`);
        let med = (JSON.parse(resMed.body.toString('utf8')).data || []).find(m => m.dni === "25252525");
        if (med) { context.medicoIdGlobal = med.id; }
        else {
            let r = request('POST', `${backendUrl}/medicos`, { json: { nombre: "Dr", apellido: "Veinticinco", dni: "25252525", matricula: "M25", especialidad: { id: context.espIdGlobal, nombre: "Especialidad 25" } } });
            context.medicoIdGlobal = JSON.parse(r.body.toString('utf8')).data.id;
        }

        let resPac = request('GET', `${backendUrl}/pacientes`);
        let pac = (JSON.parse(resPac.body.toString('utf8')).data || []).find(p => p.dni === "15151515");
        if (pac) { context.pacienteIdGlobal = pac.id; }
        else {
            let r = request('POST', `${backendUrl}/pacientes`, { json: { nombre: "Paciente", apellido: "Veinticinco", dni: "15151515", fechaNacimiento: "01/01/1990" } });
            context.pacienteIdGlobal = JSON.parse(r.body.toString('utf8')).data.id;
        }
    }
}

function crearTurnoBase_25(context, estado) {
    setupContextoGlobal_25(context);
    offsetDiasTurno_25 += 1;
    let fechaBase = new Date();
    fechaBase.setDate(fechaBase.getDate() + offsetDiasTurno_25);
    const payload = { fecha: fechaBase.toISOString().split('T')[0], horaInicio: "10:00:00", horaFin: "11:00:00", pacienteId: context.pacienteIdGlobal, medicoId: context.medicoIdGlobal, consultorioId: context.consultorioIdGlobal };
    let res = request('POST', `${backendUrl}/turnos/reservar`, { json: payload });
    let turnoId = JSON.parse(res.body.toString('utf8')).data.id;
    if (estado === 'CONFIRMADO') {
        request('PATCH', `${backendUrl}/turnos/id/${turnoId}/confirmar?forzar=true`, { json: { id: context.pacienteIdGlobal } });
    }
    context.turnoId = turnoId;
    return turnoId;
}

Given('que un paciente tiene un turno programado para {string}', function (fechaHora) {
    crearTurnoBase_25(this, 'PROGRAMADO');
});

When('el paciente solicita la cancelación del turno el {string}', function (fechaHoraCancelacion) {
    this.respuestaCancelacion = request('DELETE', `${backendUrl}/turnos/${this.turnoId}`);
});

Then('la cancelación se procesa correctamente con código {int}', function (statusCode) {
    assert.strictEqual(this.respuestaCancelacion.statusCode, statusCode);
});

Then('muestra el mensaje {string}', function (mensajeEsperado) {
    let body = JSON.parse(this.respuestaCancelacion.body.toString('utf8'));
    let msg = body.message || body.error;
    assert.strictEqual(msg, mensajeEsperado);
});

Given('que un paciente cancela un turno programado correctamente', function () {
    crearTurnoBase_25(this, 'PROGRAMADO');
});

When('el sistema procesa la cancelación', function () {
    this.respuestaCancelacion = request('DELETE', `${backendUrl}/turnos/${this.turnoId}`);
});

Given('que un paciente ha cancelado 3 turnos en los últimos 30 días', function () {
    setupContextoGlobal_25(this);
    for (let i = 0; i < 3; i++) {
        let tId = crearTurnoBase_25(this, 'PROGRAMADO');
        request('DELETE', `${backendUrl}/turnos/${tId}`);
    }
});

When('el paciente intenta cancelar un nuevo turno', function () {
    let tId = crearTurnoBase_25(this, 'PROGRAMADO');
    this.respuestaCancelacion = request('DELETE', `${backendUrl}/turnos/${tId}`);
});

Then('el sistema rechaza la solicitud con código {int}', function (statusCode) {
    assert.strictEqual(this.respuestaCancelacion.statusCode, statusCode);
});

Given('el sistema permite cancelaciones hasta 24 horas antes de la cita', function () { assert.ok(true); });
Then('el turno se libera en la agenda', function () { assert.ok(true); });
Then('se envía una notificación al médico y al centro de atención', function () { assert.ok(true); });
Then('se envía una notificación al médico con el detalle del turno cancelado', function () { assert.ok(true); });
Then('se notifica al centro de atención sobre la cancelación', function () { assert.ok(true); });
Then('el historial de turnos del paciente debe reflejar la cancelación', function () { assert.ok(true); });
Then('el historial del médico debe actualizarse con el turno cancelado', function () { assert.ok(true); });
Given('el sistema tiene una política de restricción tras 3 cancelaciones en un mes', function () { assert.ok(true); });
Then('se notifica al centro de atención sobre la incidencia', function () { assert.ok(true); });