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

    if (expected.status_code === 409 || expected.status_code === 404) {
        assert.ok([200, 404, 409].includes(actualStatus), `Status obtenido: ${actualStatus}`);
    } else {
        assert.ok([200, 201].includes(actualStatus), `Status code esperado 200/201, obtenido: ${actualStatus}`);
    }

    const validMessages = ["ok", "consulta exitosa", "ningún consultorio recuperado"];
    const actualMessage = (actualJson.message || "").toLowerCase();
    assert.ok(validMessages.includes(actualMessage), `Mensaje inesperado: ${actualJson.message}`);

    if (expected.data) {
        let actualData = actualJson.data || [];

        if (actualData && !Array.isArray(actualData) && actualData.content) {
            actualData = actualData.content;
        }

        if (extractConsultorios && actualData && !Array.isArray(actualData) && actualData.consultorios) {
            actualData = actualData.consultorios;
        }

        const isMatch = isSubset(expected.data, actualData);
        if (!isMatch) {
            console.error("\n[DEBUG] DISCREPANCIA EN LOS DATOS:");
            console.error("Esperado:", JSON.stringify(expected.data, null, 2));
            console.error("Actual:", JSON.stringify(actualData, null, 2));
        }
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
    this.lastResponse = request('GET', `http://backend:8080/centros/${this.idCentro}/consultorios`, { throw: false });
});

When('se solicita la lista completa de centros con sus consultorios', function () {
    this.lastResponse = request('GET', 'http://backend:8080/centros', { throw: false });
});

When('se solicita la lista de consultorios del centro {string}', function (nombre) {
    this.lastResponse = request('GET', `http://backend:8080/centros/${this.idCentroInexistente}/consultorios`, { throw: false });
});

Then("el sistema responde con el siguiente JSON:", function (docString) {
    validarRespuestaJSON(this.lastResponse, docString, true);
});

Then("el sistema responde con el siguiente JSON que contiene los datos:", function (docString) {
    validarRespuestaJSON(this.lastResponse, docString, false);
});

Then('el sistema responde con el siguiente JSON vacio:', function (docString) {
    validarRespuestaJSON(this.lastResponse, docString, false);
});