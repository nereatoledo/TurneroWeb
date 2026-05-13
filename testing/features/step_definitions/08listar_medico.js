const { Given, When, Then } = require('@cucumber/cucumber');
const assert = require('assert');
const request = require('sync-request');

Given('que existen {int} medicos registrados en el sistema', function (cantidad) {
    assert.ok(true); 
});

When('un usuario del sistema solicita la lista de medicos', function () {
    this.lastResponse = request('GET', 'http://backend:8080/medicos');
});

Then('el sistema responde con un JSON de medicos:', function (docString) {
    const esperado = JSON.parse(docString);
    const jsonParseado = JSON.parse(this.lastResponse.body.toString('utf8'));
    
    assert.strictEqual(this.lastResponse.statusCode, esperado.status_code);

    let dataObtenida = jsonParseado.data || [];
    
    dataObtenida = dataObtenida.map(m => ({
        nombre: m.nombre,
        apellido: m.apellido,
        dni: m.dni,
        matricula: m.matricula,
        especialidad: m.especialidad
    }));

    dataObtenida.sort((a, b) => a.dni - b.dni);
    if (esperado.data) {
        esperado.data.sort((a, b) => a.dni - b.dni);
    }

    assert.deepStrictEqual(dataObtenida, esperado.data);
});