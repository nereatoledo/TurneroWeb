const { Given, When, Then } = require('@cucumber/cucumber');
const assert = require('assert');
const request = require('sync-request');

let lastResponse;
let respuesta;

Given('que existen centros de atención creados en el sistema', function () {
    // Write code here that turns the phrase above into concrete actions
});

Given('los siguientes centros de atención han sido registrados:', function (dataTable) {
    const centros = dataTable.hashes();

    for (const fila of centros) {
        const coordsArray = fila.Coordenadas.split(',').map(coord => coord.trim());
        const coordenadasObj = {
            lat: parseFloat(coordsArray[0]),
            lng: parseFloat(coordsArray[1])
        };

        const centro = {
            nombre: fila.Nombre === "" ? null : fila.Nombre,
            direccion: fila.Dirección === "" ? null : fila.Dirección,
            localidad: fila.Localidad,
            provincia: fila.Provincia,
            telefono: fila.Teléfono ? fila.Teléfono : null,
            coordenadas: coordenadasObj
        };

        this.lastResponse = request('POST', 'http://backend:8080/centros', {
            json: centro
        });
    }
});

When('el usuario solicita la lista de centros de atención', function () {

    this.lastResponse = request('GET', 'http://backend:8080/centros');
    const bodyString = this.lastResponse.body.toString('utf8');
    const jsonParseado = JSON.parse(bodyString);

    this.respuesta = jsonParseado.data;
    this.statusCode = jsonParseado.status;
    this.statusText = jsonParseado.message;
});

Then('el sistema responde con status_code {int} y status_text {string}', function (codigo, texto) {
    assert.strictEqual(this.statusCode, codigo);
    assert.strictEqual(this.statusText, texto);
});

Then('el cuerpo de la respuesta contiene un array JSON con la siguiente estructura:', function (docString) {
    assert.strictEqual(this.respuesta, docString);
});
