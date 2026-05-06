const { Given, When } = require('@cucumber/cucumber');
const request = require('sync-request');

Given('que existe un centro de atención llamado {string}', function (nombreCentro) {
    // Hacemos un GET a la API para obtener todos los centros
    const resGet = request('GET', 'http://backend:8080/centros');
    const json = JSON.parse(resGet.body.toString('utf8'));
    const centros = json.data || [];
    
    const centro = centros.find(c => c.nombre === nombreCentro);
    
    this.idCentro = centro ? centro.id : 9999;
});

When('se registra un consultorio con el número {int} y el nombre {string}', function (numero, nombre) {
    
    const consultorio = {
        numero: nombre === "Consultorio sin número" ? null : numero,
        nombre: nombre === "" ? null : nombre
    };

    this.lastResponse = request('POST', `http://backend:8080/consultorios/centro/${this.idCentro}`, {
        json: consultorio
    });
});