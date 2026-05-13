const { Given, When, Then } = require('@cucumber/cucumber');
const assert = require('assert');
const request = require('sync-request');

const URL_BASE = 'http://backend:8080/medicos';

When('el administrador crea un médico con {string}, {string}, {string}, {string} y {string}', function (nombre, apellido, dni, matricula, especialidad) {
    
    let especialidadObj = null;
    if (especialidad && especialidad.trim() !== "") {
        especialidadObj = { nombre: especialidad.trim() };
    }

    const medico = {
        nombre: nombre === "" ? null : nombre,
        apellido: apellido === "" ? null : apellido,
        dni: dni === "" ? null : dni,
        matricula: matricula === "" ? null : matricula,
        especialidad: especialidadObj
    };

    this.lastResponse = request('POST', URL_BASE, {
        json: medico
    });
});
