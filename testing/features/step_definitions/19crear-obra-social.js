const { When } = require('@cucumber/cucumber');
const request = require('sync-request');

const URL_BASE = 'http://backend:8080/obras-sociales';

let cleared = false;

When('el administrador crea una obra social con el nombre {string} y el codigo {string}', function (nombre, codigo) {
    if (!cleared) {
        try {
            request('DELETE', URL_BASE + '/clear');
        } catch (e) {
        }
        cleared = true;
    }

    try {
        this.lastResponse = request('POST', URL_BASE, {
            json: {
                nombre: nombre === "" ? null : nombre,
                codigo: codigo === "" ? null : codigo
            }
        });
    } catch (error) {
        this.lastResponse = error.response || error;
    }
});
