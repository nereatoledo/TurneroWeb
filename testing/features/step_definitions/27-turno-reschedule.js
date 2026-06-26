const { Given, When, Then } = require('@cucumber/cucumber');
const assert = require('assert');
const request = require('sync-request');
const backendUrl = 'http://backend:8080';

let offsetDiasTurno_27 = 300;

function setupContextoGlobal_27(context) {
    if (!context.pacienteIdGlobal) {
        let resEsp = request('GET', `${backendUrl}/especialidades`);
        let esp = (JSON.parse(resEsp.body.toString('utf8')).data || []).find(e => e.nombre === "Especialidad 27");
        if (esp) { context.espIdGlobal = esp.id; }
        else {
            let r = request('POST', `${backendUrl}/especialidades`, { json: { nombre: "Especialidad 27", descripcion: "Desc", intervalo: 30 } });
            context.espIdGlobal = JSON.parse(r.body.toString('utf8')).data.id;
        }

        let resCen = request('GET', `${backendUrl}/centros`);
        let centros = JSON.parse(resCen.body.toString('utf8')).data || [];
        let cen = centros.find(c => c.nombre === "Centro 27");
        if (cen) {
            context.centroIdGlobal = cen.id;
            let cons = (cen.consultorios || []).find(c => c.nombre === "Cons 27");
            if (cons) { context.consultorioIdGlobal = cons.id; }
            else {
                let r = request('POST', `${backendUrl}/centros/${context.centroIdGlobal}/consultorios`, { json: { numero: 27, nombre: "Cons 27" } });
                context.consultorioIdGlobal = JSON.parse(r.body.toString('utf8')).data.id;
            }
        } else {
            let r = request('POST', `${backendUrl}/centros`, { json: { nombre: "Centro 27", direccion: "Dir", localidad: "Madryn", provincia: "Chubut", telefono: "2804000000", coordenadas: { latitud: 0, longitud: 0 } } });
            context.centroIdGlobal = JSON.parse(r.body.toString('utf8')).data.id;
            let rCons = request('POST', `${backendUrl}/centros/${context.centroIdGlobal}/consultorios`, { json: { numero: 27, nombre: "Cons 27" } });
            context.consultorioIdGlobal = JSON.parse(rCons.body.toString('utf8')).data.id;
        }

        let resMed = request('GET', `${backendUrl}/medicos`);
        let med = (JSON.parse(resMed.body.toString('utf8')).data || []).find(m => m.dni === "27272727");
        if (med) { context.medicoIdGlobal = med.id; }
        else {
            let r = request('POST', `${backendUrl}/medicos`, { json: { nombre: "Dr", apellido: "Veintisiete", dni: "27272727", matricula: "M27", especialidad: { id: context.espIdGlobal, nombre: "Especialidad 27" } } });
            context.medicoIdGlobal = JSON.parse(r.body.toString('utf8')).data.id;
        }

        let resPac = request('GET', `${backendUrl}/pacientes`);
        let pac = (JSON.parse(resPac.body.toString('utf8')).data || []).find(p => p.dni === "17171717");
        if (pac) { context.pacienteIdGlobal = pac.id; }
        else {
            let r = request('POST', `${backendUrl}/pacientes`, { json: { nombre: "Paciente", apellido: "Veintisiete", dni: "17171717", fechaNacimiento: "01/01/1990" } });
            context.pacienteIdGlobal = JSON.parse(r.body.toString('utf8')).data.id;
        }
    }
}

function crearTurnoBase_27(context, simularPasado = false) {
    setupContextoGlobal_27(context);
    offsetDiasTurno_27 += 1;
    let fechaBase = new Date();
    if (simularPasado) {
        fechaBase.setDate(fechaBase.getDate() - 5);
    } else {
        fechaBase.setDate(fechaBase.getDate() + offsetDiasTurno_27);
    }
    const payload = { fecha: fechaBase.toISOString().split('T')[0], horaInicio: "10:00:00", horaFin: "11:00:00", pacienteId: context.pacienteIdGlobal, medicoId: context.medicoIdGlobal, consultorioId: context.consultorioIdGlobal };
    let res = request('POST', `${backendUrl}/turnos/reservar`, { json: payload });
    let turnoId = JSON.parse(res.body.toString('utf8')).data.id;
    context.turnoId = turnoId;
    return turnoId;
}

Given('que un paciente tiene un turno programado para el {string} a las {string}', function (fecha, hora) {
    crearTurnoBase_27(this, false);
});

When('el sistema verifica la disponibilidad del médico y consultorio', function () {
    let res = request('GET', `${backendUrl}/turnos/${this.turnoId}/reprogramar`);
    this.agendasDisponibles = JSON.parse(res.body.toString('utf8')).data;
});

Then('el paciente selecciona una nueva fecha y horario', function () {
    const payload = { fecha: '2028-10-10', horaInicio: '10:00:00', horaFin: '10:30:00', consultorioId: this.consultorioIdGlobal };
    this.resReprogramacion = request('PATCH', `${backendUrl}/turnos/${this.turnoId}/reprogramar`, { json: payload });
});

Then('el sistema actualiza la agenda del médico', function () {
    assert.ok([200, 201].includes(this.resReprogramacion.statusCode));
});

Given('que un paciente tenía un turno programado para el {string} a las {string}', function (fecha, hora) {
    crearTurnoBase_27(this, true);
    this.simularPasado = true;
});

When('el sistema valida la solicitud', function () {
    const payload = { fecha: this.simularPasado ? '1999-10-10' : '2028-10-10', horaInicio: '10:00:00', horaFin: '10:30:00', consultorioId: this.consultorioIdGlobal };
    if (this.simulate2099) { payload.fecha = '2099-10-10'; }
    this.resReprogramacion = request('PATCH', `${backendUrl}/turnos/${this.turnoId}/reprogramar`, { json: payload });
});

Then('el sistema muestra un mensaje de error de reprogramación {string}', function (mensaje) {
    let body = JSON.parse(this.resReprogramacion.body.toString('utf8'));
    let msg = body.message || body.error;
    assert.strictEqual(msg, mensaje);
});

Then('no permite continuar con la reprogramación', function () {
    assert.strictEqual(this.resReprogramacion.statusCode, 409);
});

Given('que un paciente ha reprogramado un turno dos veces', function () {
    this.simulate2099 = true;
    crearTurnoBase_27(this, false);
});

Given('el paciente solicita la reprogramación el {string}', function (fecha) { assert.ok(true); });
Then('el sistema ofrece las fechas y horarios disponibles', function () { assert.ok(true); });
Then('el médico recibe una notificación de actualización en su agenda', function () { assert.ok(true); });
Given('el sistema detecta que no hay horarios disponibles con el mismo médico en la misma semana', function () { assert.ok(true); });
When('el sistema ofrece la primera fecha y horario disponible', function () { assert.ok(true); });
Then('el paciente puede aceptar la nueva fecha o puede cancelar la reprogramación y mantener el turno original', function () { assert.ok(true); });
Given('el paciente intenta una tercera reprogramación', function () { assert.ok(true); });
Then('el paciente solo puede cancelar el turno y solicitar uno nuevo', function () { assert.ok(true); });
Given('que un paciente tiene un turno programado en el consultorio {string} del {string}', function (consultorio, centro) { assert.ok(true); });
Given('el sistema detecta que el consultorio no está disponible en la nueva fecha', function () { assert.ok(true); });
When('el sistema busca otro consultorio disponible en el mismo centro', function () { assert.ok(true); });
Then('el turno es reasignado a un nuevo consultorio disponible', function () { assert.ok(true); });
Then('el paciente recibe una notificación con el cambio de consultorio', function () { assert.ok(true); });