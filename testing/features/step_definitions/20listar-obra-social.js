const { Given, When, Then } = require('@cucumber/cucumber');
const assert = require('assert');
const request = require('sync-request');
const jsonDiff = require('json-diff');

const URL_BASE = 'http://backend:8080/obras-sociales';

Given('que existen {int} obras sociales registradas en el sistema', function (expectedCount) {
    const res = request('GET', URL_BASE);
    const count = JSON.parse(res.getBody('utf8')).data.length;
    assert.strictEqual(count, expectedCount);
});

When('un usuario del sistema solicita la lista de obras sociales', function () {
    this.lastResponse = request('GET', URL_BASE);
});

Then('el sistema responde con un JSON de las obras sociales:', function (docString) {
    const jsonEsperado = JSON.parse(docString);
    const jsonReal = JSON.parse(this.lastResponse.getBody('utf8'));

    assert.strictEqual(this.lastResponse.statusCode, 200);
    assert.strictEqual(jsonReal.status, jsonEsperado.status);
    assert.strictEqual(jsonReal.message, jsonEsperado.message);

    const sortData = (data) => {
        return data.map(item => {
            const { id, ...sinId } = item;
            return sinId;
        }).sort((a, b) => a.nombre.localeCompare(b.nombre));
    };

    const esperado = sortData(jsonEsperado.data);
    const real = sortData(jsonReal.data);

    const diff = jsonDiff.diff(esperado, real);
    if (diff) {
        console.error("Diferencias encontradas en obras sociales:");
        console.error(jsonDiff.diffString(esperado, real));
        assert.fail("Los datos no coinciden. Revisa la diferencia arriba.");
    }
});
