const { Given, When } = require('@cucumber/cucumber');
const request = require('sync-request');
const assert = require('assert');

Given('que existe un centro de atención llamado {string}', function (nombreCentro) {
    const resGet = request('GET', 'http://backend:8080/centros', { throw: false });
    const json = resGet.statusCode === 200 ? JSON.parse(resGet.body.toString('utf8')) : {};
    const centros = json.data || [];
    const centro = centros.find(c => c.nombre === nombreCentro);

    if (centro) {
        this.idCentro = centro.id;
    } else {
        // 1. Creamos el centro
        const resPost = request('POST', 'http://backend:8080/centros', {
            json: { nombre: nombreCentro, direccion: `Dir ${Date.now()}`, localidad: "Madryn", provincia: "Chubut", telefono: "2804000000", coordenadas: { latitud: 0, longitud: 0 } },
            throw: false
        });
        assert.ok([200, 201].includes(resPost.statusCode), `Error creando centro '${nombreCentro}': ${resPost.body.toString('utf8')}`);
        this.idCentro = JSON.parse(resPost.body.toString('utf8')).data.id;

        // 2. Inyección de datos: Le creamos los 6 consultorios que exige el Feature 06
        if (nombreCentro === "Centro Médico Esperanza") {
            for (let i = 1; i <= 6; i++) {
                request('POST', `http://backend:8080/centros/${this.idCentro}/consultorios`, {
                    json: { numero: 500 + i, nombre: `Consultorio ${i}` },
                    throw: false
                });
            }
        }
    }
});

When('se registra un consultorio con el número {int} y el nombre {string}', function (numero, nombre) {
    const consultorio = {
        numero: nombre === "Consultorio sin número" ? null : numero,
        nombre: nombre === "" ? null : nombre
    };
    this.lastResponse = request('POST', `http://backend:8080/centros/${this.idCentro}/consultorios`, {
        json: { numero: consultorio.numero, nombre: consultorio.nombre }, throw: false
    });
});