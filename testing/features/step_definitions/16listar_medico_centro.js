const { Given, When, Then } = require('@cucumber/cucumber');
const request = require('sync-request');
const assert = require('assert');
const jsonDiff = require('json-diff');

Given('que existen médicos asociados a centros médicos en el sistema', function () {
    return 'pasado';
});

When('un usuario del sistema solicita la lista de médicos asociados', function () {
    this.lastResponse = request('GET', 'http://backend:8080/centros/medicos');
});

When('un usuario del sistema solicita la lista de médicos asociadas al centro {string}', function (nombreCentro) {
    const url = `http://backend:8080/centros/${encodeURIComponent(nombreCentro)}/medicos`;
    this.lastResponse = request('GET', url);
});

Then('el sistema responde con un JSON de los centros y sus médicos:', function (docString) {
    const jsonEsperado = JSON.parse(docString);
    const jsonReal = JSON.parse(this.lastResponse.getBody('utf8'));

    const statusCodeReal = jsonReal.status || jsonReal.status_code;
    const statusTextReal = jsonReal.message || jsonReal.status_text;

    assert.strictEqual(statusCodeReal, jsonEsperado.status_code);
    assert.strictEqual(statusTextReal, jsonEsperado.status_text);
    
    const sortData = (data) => {
        return data.sort((a, b) => a.centro_de_atencion.localeCompare(b.centro_de_atencion))
            .map(centro => ({
                ...centro,
                medicos: centro.medicos.sort((m1, m2) => m1.dni - m2.dni).map(m => {
                    const { id, ...medicoSinId } = m;
                    return medicoSinId;
                })
            }));
    };

    const esperado = sortData(jsonEsperado.data);
    const real = sortData(jsonReal.data);

    const diff = jsonDiff.diff(esperado, real);
    
    if (diff) {
        console.error("Diferencias encontradas entre JSON esperado y real:");
        console.error(jsonDiff.diffString(esperado, real));
        assert.fail("Los datos no coinciden. Revisa la diferencia arriba.");
    }
});