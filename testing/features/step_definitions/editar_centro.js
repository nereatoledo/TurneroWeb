const { When } = require('@cucumber/cucumber');
const request = require('sync-request');

When('el administrador modifica los datos del centro de atención {string} con los siguientes atributos:', function (nombreOriginal, dataTable) {
    const datosNuevos = dataTable.hashes()[0];

    const resGet = request('GET', 'http://backend:8080/centros');
    const centros = JSON.parse(resGet.body.toString('utf8')).data || [];
    const centroOriginal = centros.find(c => c.nombre === nombreOriginal);
    
    const idCentro = centroOriginal ? centroOriginal.id : -1;
    const telefonoOriginal = centroOriginal ? centroOriginal.telefono : null;

    let coordenadasObj = null;
    const coordStr = datosNuevos.Coordenadas;
    
    if (coordStr && coordStr.includes(',') && coordStr !== "abc, xyz") {
        const partes = coordStr.split(',');
        coordenadasObj = { 
            latitud: parseFloat(partes[0].trim()), 
            longitud: parseFloat(partes[1].trim()) 
        };
    } else if (coordStr === "abc, xyz") {
        coordenadasObj = null; 
    }

    const centroActualizado = {
        id: idCentro,
        nombre: datosNuevos.Nombre === "" ? null : datosNuevos.Nombre,
        direccion: datosNuevos.Dirección === "" ? null : datosNuevos.Dirección,
        localidad: datosNuevos.Localidad === "" ? null : datosNuevos.Localidad,
        provincia: datosNuevos.Provincia === "" ? null : datosNuevos.Provincia,
        telefono: telefonoOriginal,
        coordenadas: coordenadasObj
    };

    this.lastResponse = request('PUT', 'http://backend:8080/centros', {
        json: centroActualizado
    });
});