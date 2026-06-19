const { Given, When } = require('@cucumber/cucumber');
const request = require('sync-request');

function buscarIdObraSocial(nombreEsperado) {
    try {
        const res = request('GET', 'http://backend:8080/obras-sociales');
        const lista = JSON.parse(res.getBody('utf8')).data;
        const encontrada = lista.find(os => os.nombre === nombreEsperado);
        return encontrada ? encontrada.id : 1;
    } catch (e) {
        return 1;
    }
}

Given('que existe la obra social {string}', function (nombreObraSocial) {
    if (!nombreObraSocial || nombreObraSocial.trim() === "") {
        return;
    }
    const res = request('GET', 'http://backend:8080/obras-sociales');
    const list = JSON.parse(res.getBody('utf8')).data;
    const found = list.some(os => os.nombre === nombreObraSocial);
    if (!found) {
        request('POST', 'http://backend:8080/obras-sociales', {
            json: {
                nombre: nombreObraSocial,
                codigo: nombreObraSocial.replace(/[^a-zA-Z0-9]/g, '').substring(0, 10).toUpperCase()
            }
        });
    }
});

When('el administrador crea un paciente con {string}, {string}, {string}, {string} y {string}', function (nombre, apellido, dni, fechaNacimiento, obraSocial) {
    const payload = {};
    if (nombre !== "") payload.nombre = nombre;
    if (apellido !== "") payload.apellido = apellido;
    if (dni !== "") payload.dni = dni;
    
    if (fechaNacimiento !== "") {
        payload.fechaNacimiento = fechaNacimiento;
    }
    
    if (obraSocial && obraSocial.trim() !== "") {
        payload.obraSocial = { id: buscarIdObraSocial(obraSocial), nombre: obraSocial };
    }

    try {
        this.lastResponse = request('POST', 'http://backend:8080/pacientes', {
            json: payload, throw: false
        });
    } catch (error) {
        this.lastResponse = error.response || error;
    }
});