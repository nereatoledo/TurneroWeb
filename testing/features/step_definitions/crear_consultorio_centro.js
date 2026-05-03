const { Given, When, Then } = require('@cucumber/cucumber');
const assert = require('assert');
const request = require('sync-request');

Given('que existe un centro de atención llamado {string}', function (nombre) {
});

When('se registra un consultorio con el número {string} y el nombre {string}', function (numero, nombre) {
    const consultorio = {
        numero: numero === "" ? null : numero,
        nombre: nombre === "" ? null : nombre
    };

    this.lastResponse = request('POST', 'http://backend:8080/consultorios', {
        json: consultorio
    });
});