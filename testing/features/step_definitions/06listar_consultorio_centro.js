const { Given, When, Then } = require('@cucumber/cucumber');
const assert = require('assert');
const request = require('sync-request');

const isSubset = (subset, superset) => {
    if (typeof subset !== 'object' || subset === null || typeof superset !== 'object' || superset === null) {
        return subset === superset;
    }
    if (Array.isArray(subset)) {
        if (!Array.isArray(superset)) return false;
        return subset.every(subItem => superset.some(superItem => isSubset(subItem, superItem)));
    }
    return Object.keys(subset).every(key => {
        if (key === "nombre_consultorio" && superset["nombre"] !== undefined) {
            return isSubset(subset[key], superset["nombre"]);
        }
        if (key === "centro_atencion" && superset["nombre"] !== undefined) {
            return isSubset(subset[key], superset["nombre"]);
        }
        if (!superset.hasOwnProperty(key)) return false;
        
        return isSubset(subset[key], superset[key]);
    });
};

const validarRespuestaJSON = (lastResponse, docString, extractConsultorios = false) => {
    const expected = JSON.parse(docString);
    const actualJson = JSON.parse(lastResponse.body.toString('utf8'));
    
    const actualStatus = lastResponse.statusCode || actualJson.status;
    const actualMessage = actualJson.message || "";

    assert.equal(actualStatus, expected.status_code, `Status code esperado: ${expected.status_code}, obtenido: ${actualStatus}`);
    assert.equal(actualMessage.toLowerCase(), expected.status_text.toLowerCase());

    if (expected.data) {
        let actualData = actualJson.data;
        if (extractConsultorios && actualData && !Array.isArray(actualData) && actualData.consultorios) {
            actualData = actualData.consultorios;
        }
        const isMatch = isSubset(expected.data, actualData);
        assert.ok(isMatch, "Los datos obtenidos no coinciden con la estructura o valores del JSON esperado.");
    }
};


Given('que existen múltiples centros de atención registrados', function () {
    assert.ok(true);
});

Given('que el centro de atención llamado {string} no está registrado', function (nombre) {
    this.idCentroInexistente = 99999;
});

When('se solicita la lista de consultorios del centro', function () {
    this.lastResponse = request('GET', `http://backend:8080/centros/id/${this.idCentro}`);
});

When('se solicita la lista completa de centros con sus consultorios', function () {
    this.lastResponse = request('GET', 'http://backend:8080/centros');
});

When('se solicita la lista de consultorios del centro {string}', function (nombre) {
    this.lastResponse = request('GET', `http://backend:8080/centros/id/${this.idCentroInexistente}`);
});

Then("el sistema responde con el siguiente JSON:", function (docString) {
    validarRespuestaJSON(this.lastResponse, docString, true);
});

Then("el sistema responde con el siguiente JSON que contiene los datos:", function (docString) {
    validarRespuestaJSON(this.lastResponse, docString, false);
});

Then('el sistema responde con el siguiente JSON vacio:', function (docString) {
    assert.strictEqual(this.lastResponse.statusCode, 404);
});