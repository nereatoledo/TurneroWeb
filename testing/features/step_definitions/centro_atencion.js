const { Given, When, Then } = require('@cucumber/cucumber');
const assert = require('assert');
const fetch = require('node-fetch');

let lastResponse;

Given('que existe un sistema de gestión de centros de atención', function () {
});

When('el administrador ingresa los datos del centro de atención: {string}, {string}, {string}, {string} y {string}', async function (nombre, direccion, localidad, provincia, coordenadas) {
    
    let lat = 0;
    let lon = 0;
    if (coordenadas && coordenadas.includes(',')) {
        const partes = coordenadas.split(',');
        lat = parseFloat(partes[0].trim());
        lon = parseFloat(partes[1].trim());
    }
    
    const centro = {
        nombre: nombre === "" ? null : nombre,
        direccion: direccion === "" ? null : direccion,
        localidad: localidad,
        provincia: provincia,
        coordenadas: {
            latitud: lat,
            longitud: lon
        }
    };

    lastResponse = await fetch('http://backend:8080/centros', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(centro)
    });
});

Then('el sistema responde con {int} y {string}', async function (codigoEsperado, textoEsperado) {
    assert.strictEqual(lastResponse.status, codigoEsperado, `Falló: Se esperaba ${codigoEsperado} pero devolvió ${lastResponse.status}`);
});