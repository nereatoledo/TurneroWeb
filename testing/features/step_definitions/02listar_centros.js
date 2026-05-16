const { Given, When, Then } = require('@cucumber/cucumber');
const assert = require('assert');
const request = require('sync-request');

function limpiarCentro(centro) {
    const limpio = { ...centro };

    delete limpio.id;
    delete limpio.telefono;
    delete limpio.consultorios;
    delete limpio.especialidades;
    
    if (limpio.coordenadas) {
        delete limpio.coordenadas.id;
    }

    return limpio;
}

function ordenarCentros(arr) {
    return arr.sort((a, b) => {
        const nombreA = a.nombre || '';
        const nombreB = b.nombre || '';
        return nombreA.localeCompare(nombreB);
    });
}

Given('que existen centros de atención creados en el sistema', function () {
});

Given('los siguientes centros de atención han sido registrados:', function (dataTable) {
    const centros = dataTable.hashes();

    for (const fila of centros) {
        const coordsArray = fila.Coordenadas.split(',').map(coord => coord.trim());
        const coordenadasObj = {
            latitud: parseFloat(coordsArray[0]),
            longitud: parseFloat(coordsArray[1])
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
    const bodyString = this.lastResponse.body.toString('utf8');
    const jsonParseado = JSON.parse(bodyString);
    assert.strictEqual(jsonParseado.status, 200);

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
    const validMessages = ["OK", "Consulta exitosa"];
    assert.ok(
        validMessages.includes(this.statusText), 
        `Se esperaba uno de los siguientes mensajes: ${validMessages.join(" o ")}, pero se obtuvo: ${this.statusText}`
    );
});
Then('el cuerpo de la respuesta contiene un array JSON con la siguiente estructura:', function (docString) {
    
    let centrosEsperados = JSON.parse(docString).data;
    let centrosObtenidos = this.respuesta;

    centrosEsperados = centrosEsperados.map(limpiarCentro);
    centrosObtenidos = centrosObtenidos.map(limpiarCentro);

    centrosEsperados = ordenarCentros(centrosEsperados);
    centrosObtenidos = ordenarCentros(centrosObtenidos);

    assert.deepStrictEqual(centrosObtenidos, centrosEsperados);
});