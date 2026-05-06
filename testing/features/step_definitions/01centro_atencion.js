const { Given, When, Then } = require('@cucumber/cucumber');
const assert = require('assert');
const request = require('sync-request');

Given('que existe un sistema de gestión de centros de atención', function () {
});

When('el administrador ingresa los datos del centro de atención: {string}, {string}, {string}, {string}, {string} y {string}', function (nombre, direccion, localidad, provincia, telefono, coordenadas) {
    let coordenadasObj = null;

    if (coordenadas && coordenadas.includes(',') && coordenadas !== "abc, xyz") {
        const partes = coordenadas.split(',');
        const lat = parseFloat(partes[0].trim());
        const lon = parseFloat(partes[1].trim());
        
        if (!isNaN(lat) && !isNaN(lon)) {
            coordenadasObj = { latitud: lat, longitud: lon };
        }
    }
    
    const centro = {
        nombre: nombre === "" ? null : nombre,
        direccion: direccion === "" ? null : direccion,
        localidad: localidad,
        provincia: provincia,
        telefono: telefono === "" ? null : telefono,
        coordenadas: coordenadasObj
    };

    this.lastResponse = request('POST', 'http://backend:8080/centros', {
        json: centro
    });
});

Then('el sistema responde con {int} y {string}', function (codigoEsperado, textoEsperado) {
    assert.strictEqual(this.lastResponse.statusCode, codigoEsperado);
    const respuestaJson = JSON.parse(this.lastResponse.body.toString('utf8'));
    assert.strictEqual(respuestaJson.message, textoEsperado);
});