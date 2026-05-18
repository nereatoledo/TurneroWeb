const { Given, When, Then } = require('@cucumber/cucumber');
const assert = require('assert');
const request = require('sync-request');

Given('que existen especialidades asociadas a centros médicos en el sistema', function () {
});

When('un usuario del sistema solicita la lista de especialidades asociadas', function () {
    this.lastResponse = request('GET', 'http://backend:8080/centros/especialidades');
});

When('un usuario del sistema solicita la lista de especialidades asociadas al centro {string}', function (nombreCentro) {
    let idCentro = 0;
    try {
        const resCentros = request('GET', 'http://backend:8080/centros');
        if (resCentros.statusCode === 200) {
            const bodyCentros = JSON.parse(resCentros.body.toString('utf8'));
            const centro = bodyCentros.data.find(c => c.nombre === nombreCentro);
            if (centro) idCentro = centro.id;
        }
    } catch (error) {
        console.error(error.message);
    }

    this.lastResponse = request('GET', `http://backend:8080/centros/${idCentro}/especialidades`);
});

Then('el sistema responde con un JSON de los centros y sus especialidades:', function (docString) {
    const esperado = JSON.parse(docString);
    const actual = JSON.parse(this.lastResponse.body.toString('utf8'));

    const statusActual = actual.status_code || actual.status;
    const msgActual = actual.status_text || actual.message;

    assert.strictEqual(statusActual, esperado.status_code);
    assert.strictEqual(msgActual, esperado.status_text);
    assert.strictEqual(actual.data.length, esperado.data.length);

    const sortAlfabetico = (a, b) => a.localeCompare(b);

    esperado.data.forEach(espCentroEsperado => {
        const centroActual = actual.data.find(c => c.centro_de_atencion === espCentroEsperado.centro_de_atencion);
        
        assert.ok(centroActual);

        const espEsperadasSorted = [...espCentroEsperado.especialidades].sort(sortAlfabetico);
        const espActualesSorted = [...centroActual.especialidades].sort(sortAlfabetico);

        assert.deepStrictEqual(espActualesSorted, espEsperadasSorted);
    });
});

Then('el sistema responde con un JSON de las especialidades de ese centro:', function (docString) {
    const esperado = JSON.parse(docString);
    const actual = JSON.parse(this.lastResponse.body.toString('utf8'));

    const statusActual = actual.status_code || actual.status;
    const msgActual = actual.status_text || actual.message;

    assert.strictEqual(statusActual, esperado.status_code);
    assert.strictEqual(msgActual, esperado.status_text);

    const sortAlfabetico = (a, b) => a.localeCompare(b);
    const espEsperadasSorted = [...esperado.data].sort(sortAlfabetico);
    const espActualesSorted = [...actual.data].sort(sortAlfabetico);

    assert.deepStrictEqual(espActualesSorted, espEsperadasSorted);
});