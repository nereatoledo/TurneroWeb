const { When, Given } = require('@cucumber/cucumber');
const assert = require('assert');
const request = require('sync-request');

Given('existen {int} centros de atención registrados en el sistema:', function (cantidad, dataTable) {
    const centros = dataTable.hashes();
    assert.ok(centros.length > 0);
});

When('el administrador asocia la especialidad {string} al centro de atención {string}', function (nombreEspecialidad, nombreCentro) {
    this.centrosMap = {};
    this.especialidadesMap = {};

    try {
        const resCentros = request('GET', 'http://backend:8080/centros');
        if (resCentros.statusCode === 200) {
            const bodyCentros = JSON.parse(resCentros.body.toString('utf8'));
            if (bodyCentros.data) {
                bodyCentros.data.forEach(c => this.centrosMap[c.nombre] = c.id);
            }
        }
    } catch (error) {
        console.error(error.message);
    }

    try {
        const resEsp = request('GET', 'http://backend:8080/especialidades');
        if (resEsp.statusCode === 200) {
            const bodyEsp = JSON.parse(resEsp.body.toString('utf8'));
            if (bodyEsp.data) {
                bodyEsp.data.forEach(e => this.especialidadesMap[e.nombre] = e.id);
            }
        }
    } catch (error) {
        console.error(error.message);
    }

    const idCentro = this.centrosMap[nombreCentro] || 0;
    const idEspecialidad = this.especialidadesMap[nombreEspecialidad] || 0;

    const url = `http://backend:8080/centros/${idCentro}/especialidades/${idEspecialidad}`;

    try {
        this.lastResponse = request('POST', url);
        const jsonParseado = JSON.parse(this.lastResponse.body.toString('utf8'));
        
        this.statusCode = jsonParseado.status;
        this.statusText = jsonParseado.message;
    } catch (err) {
        if (err.statusCode) {
            this.statusCode = err.statusCode;
            if (err.body) {
                try {
                    const jsonParseado = JSON.parse(err.body.toString('utf8'));
                    this.statusCode = jsonParseado.status || this.statusCode;
                    this.statusText = jsonParseado.message;
                } catch (e) {
                    this.statusText = err.body.toString('utf8');
                }
            }
        } else {
            throw err;
        }
    }
});