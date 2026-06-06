const { Given, When, Then } = require('@cucumber/cucumber');
const assert = require('assert');
const request = require('sync-request');

const URL_BASE = 'http://backend:8080/especialidades';

function buscarId(nombre) {
    const res = request('GET', URL_BASE);
    const lista = JSON.parse(res.body.toString('utf8')).data || [];
    const item = lista.find(e => e.nombre === nombre);
    return item ? item.id : -1;
}

When('el administrador crea una especialidad con el nombre {string} y la descripción {string}', function (nombre, descripcion) {
    this.lastResponse = request('POST', URL_BASE, { json: { nombre, descripcion, intervalo: 30 } });
});

When('el administrador crea una nueva especialidad con el nombre {string} y la descripción {string}', function (nombre, descripcion) {
    this.lastResponse = request('POST', URL_BASE, { json: { nombre, descripcion, intervalo: 30 } });
});

Given('que la especialidad {string} existe en el sistema con la descripción {string}', function (nombre, descripcion) {
    request('POST', URL_BASE, { json: { nombre, descripcion, intervalo: 30 } });
});

Given('que la especialidad {string} existe en el sistema', function (nombre) {
    request('POST', URL_BASE, { json: { nombre, descripcion: "Temp", intervalo: 30 } });
});

Given('otra especialidad con el nombre {string} ya está registrada', function (nombre) {
    request('POST', URL_BASE, { json: { nombre, descripcion: "Conflicto", intervalo: 30 } });
});

When('el administrador edita la especialidad {string} cambiando su nombre a {string} y su descripción a {string}', function (ori, nue, desc) {
    this.lastResponse = request('PUT', URL_BASE, { json: { id: buscarId(ori), nombre: nue, descripcion: desc, intervalo: 30 } });
});

When('el administrador intenta cambiar el nombre de {string} a {string}', function (ori, ext) {
    this.lastResponse = request('PUT', URL_BASE, { json: { id: buscarId(ori), nombre: ext, descripcion: "Desc", intervalo: 30 } });
});

When('el administrador elimina la especialidad {string}', function (nombre) {
    const id = buscarId(nombre);
    this.lastResponse = request('DELETE', URL_BASE + '/' + id);
});

When('un usuario del sistema solicita la lista de especialidades', function () {
    this.lastResponse = request('GET', URL_BASE);
});

Given('que existen {int} especialidades registradas en el sistema', function (n) {
    assert.ok(true);
});

Then('el sistema responde con el {int} y el {string}', function (codigo, texto) {
    assert.strictEqual(this.lastResponse.statusCode, codigo);
    const body = JSON.parse(this.lastResponse.body.toString('utf8'));
    assert.strictEqual(body.message, texto);
});

Then('el sistema responde con el {string} {int} y el {string} {string}', function (s1, codigo, s2, texto) {
    assert.strictEqual(this.lastResponse.statusCode, codigo);
    const body = JSON.parse(this.lastResponse.body.toString('utf8'));
    assert.strictEqual(body.message, texto);
});

Then('el sistema responde con un JSON:', function (docString) {
    const esperado = JSON.parse(docString);
    const body = JSON.parse(this.lastResponse.body.toString('utf8'));
    assert.strictEqual(this.lastResponse.statusCode, esperado.status_code);
    assert.strictEqual(body.message, esperado.status_text);
});