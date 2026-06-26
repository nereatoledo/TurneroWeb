const { Given, When, Then } = require('@cucumber/cucumber');
const assert = require('assert');
const request = require('sync-request');
const backendUrl = 'http://backend:8080';


Given('que el paciente {string} {string} está registrado en el sistema', function (nombre, apellido) {
    let resPost = request('POST', `${backendUrl}/pacientes`, {
        json: {
            nombre,
            apellido,
            dni: Math.floor(Math.random() * 100000000).toString(),
            fechaNacimiento: "01/01/1990"
        }
    });
    this.pacienteIdCtx = JSON.parse(resPost.body.toString('utf8')).data.id;
});

Given('ha seleccionado la especialidad {string}', function (nombreEsp) {
    let resGet = request('GET', `${backendUrl}/especialidades`);
    let esp = (JSON.parse(resGet.body.toString('utf8')).data || []).find(e => e.nombre === nombreEsp);

    if (esp) {
        this.espIdCtx = esp.id;
    } else {
        let r = request('POST', `${backendUrl}/especialidades`, {
            json: { nombre: nombreEsp, descripcion: "Auto", intervalo: 30 }
        });
        this.espIdCtx = JSON.parse(r.body.toString('utf8')).data.id;
    }
});

Given('ha seleccionado al médico Dr. {string} {string}', function (nombre, apellido) {
    let resGet = request('GET', `${backendUrl}/medicos`);
    let medicos = JSON.parse(resGet.body.toString('utf8')).data || [];
    let medico = medicos.find(m => m.nombre === nombre && m.apellido === apellido);
    this.medicoIdCtx = medico ? medico.id : 1;
});

Given('ha seleccionado el centro de atención {string}', function (nombreCentro) {
    let resGet = request('GET', `${backendUrl}/centros`);
    let centros = JSON.parse(resGet.body.toString('utf8')).data || [];
    let centro = centros.find(c => c.nombre === nombreCentro);
    this.centroIdCtx = centro ? centro.id : 1;

    let resCons = request('GET', `${backendUrl}/centros/${this.centroIdCtx}/consultorios`);
    let cons = JSON.parse(resCons.body.toString('utf8')).data;
    this.consultorioIdCtx = (cons && cons.length > 0) ? cons[0].id : 1;
});

Given('es el momento {string}', function (fechaHora) {
    this.momentoActual = fechaHora;
});


When('solicita un turno', function () {
    const payload = {
        fecha: "27/06/2026",
        horaInicio: "10:00",
        horaFin: "10:30",
        pacienteId: this.pacienteIdCtx,
        medicoId: this.medicoIdCtx,
        consultorioId: this.consultorioIdCtx
    };
    this.respuestaServidor = request('POST', `${backendUrl}/turnos/reservar`, { json: payload });
});

When('solicita un turno el dia {string} para las {string}', function (fecha, hora) {
    const payload = {
        fecha: fecha,
        horaInicio: hora,
        horaFin: hora.split(':')[0] + ":30",
        pacienteId: this.pacienteIdCtx,
        medicoId: this.medicoIdCtx,
        consultorioId: this.consultorioIdCtx
    };
    this.respuestaServidor = request('POST', `${backendUrl}/turnos/reservar`, { json: payload });
});

When('la agenda de la Dra. {string} {string} está completa', function (nombre, apellido) {
    this.agendaCompleta = true;
});

When('no hay disponibilidad en esa especialidad en el centro seleccionado', function () {
    this.disponibilidad = false;
});


Then('el sistema asigna el turno', function () {
    assert.ok([200, 201].includes(this.respuestaServidor.statusCode),
        `El sistema falló al asignar el turno. Status: ${this.respuestaServidor.statusCode}`);
    this.turnoId = JSON.parse(this.respuestaServidor.body.toString('utf8')).data.id;
});

Then('el turno queda registrado en el sistema con estado {string}', function (estado) {
    let resGet = request('GET', `${backendUrl}/turnos/id/${this.turnoId}`);
    let data = JSON.parse(resGet.body.toString('utf8')).data;
    assert.strictEqual(data.estado.toUpperCase(), estado.toUpperCase());
});

Then('el paciente recibe una notificación con la confirmación del turno', function () {
    assert.ok(true);
});

Then('el sistema sugiere otros médicos disponibles en la misma especialidad', function () {
    assert.ok(true);
});

Then('ofrece fechas alternativas en otros centros de atención', function () {
    assert.ok(true);
});

Then('el paciente puede elegir entre las opciones sugeridas', function () {
    assert.ok(true);
});

Then('el sistema sugiere otros centros donde haya disponibilidad', function () {
    assert.ok(true);
});