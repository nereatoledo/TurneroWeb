const { Given, When, Then } = require('@cucumber/cucumber');
const assert = require('assert');
const request = require('sync-request');
const URL_BASE = 'http://backend:8080/pacientes';
Given('que existen {int} pacientes registrados en el sistema', function (expectedCount) {
    const res = request('GET', URL_BASE);
    const count = JSON.parse(res.body.toString('utf8')).data.length;
    assert.strictEqual(count, expectedCount);
});
When('un usuario del sistema solicita la lista de pacientes', function () {
    this.lastResponse = request('GET', URL_BASE);
});
Then('el sistema responde con los siguientes detalles:', function (docString) {
    const jsonEsperado = JSON.parse(docString);
    const bodyRespuesta = JSON.parse(this.lastResponse.body.toString('utf8'));
    assert.strictEqual(bodyRespuesta.status, jsonEsperado.status);
    const limpiarObjeto = (paciente) => {
        const pLimpio = {
            nombre: paciente.nombre,
            apellido: paciente.apellido,
            dni: paciente.dni,
            fechaNacimiento: paciente.fechaNacimiento
        };
        if (paciente.obraSocial) {
            pLimpio.obraSocial = {
                nombre: paciente.obraSocial.nombre,
                codigo: paciente.obraSocial.codigo
            };
        }
        if (pLimpio.fechaNacimiento && pLimpio.fechaNacimiento.includes('-')) {
            const [anio, mes, dia] = pLimpio.fechaNacimiento.split('-');
            pLimpio.fechaNacimiento = `${dia}/${mes}/${anio}`;
        }
        return pLimpio;
    };
    const esperadosLimpios = jsonEsperado.data.map(limpiarObjeto);
    const recibidosLimpios = bodyRespuesta.data.map(limpiarObjeto);
    esperadosLimpios.forEach(esp => {
        const encontrado = recibidosLimpios.some(rec => rec.dni === esp.dni);
        assert.ok(encontrado, `Falta el paciente con DNI ${esp.dni} en la respuesta del servidor.`);
    });
});
