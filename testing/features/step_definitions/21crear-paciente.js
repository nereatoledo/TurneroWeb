const { Given, When } = require('@cucumber/cucumber');
const request = require('sync-request');

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
    const payload = {
        nombre: nombre === "" ? null : nombre,
        apellido: apellido === "" ? null : apellido,
        dni: dni === "" ? null : dni,
        fechaNacimiento: fechaNacimiento === "" ? null : fechaNacimiento,
        obraSocial: (obraSocial && obraSocial.trim() !== "") ? { nombre: obraSocial } : null
    };

    try {
        this.lastResponse = request('POST', 'http://backend:8080/pacientes', {
            json: payload
        });
    } catch (error) {
        this.lastResponse = error.response || error;
    }
});
