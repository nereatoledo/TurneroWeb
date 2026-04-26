const { When } = require('@cucumber/cucumber');
const request = require('sync-request');

When('el administrador modifica los datos del centro de atención {string} con los siguientes atributos:', function (nombreOriginal, dataTable) {
    const datosNuevos = dataTable.hashes()[0];

    // 1. Buscamos el centro original para obtener su ID y Teléfono real del backend
    const resGet = request('GET', 'http://backend:8080/centros');
    const centros = JSON.parse(resGet.body.toString('utf8')).data || [];
    const centroOriginal = centros.find(c => c.nombre === nombreOriginal);
    
    // Si no existe, usamos un ID inválido para que el backend salte (según tu validación de id <= 0)
    const idCentro = centroOriginal ? centroOriginal.id : -1;
    const telefonoOriginal = centroOriginal ? centroOriginal.telefono : null;

    // 2. Procesamos las coordenadas para el objeto Point de Java
    let coordenadasObj = null;
    const coordStr = datosNuevos.Coordenadas;
    
    if (coordStr && coordStr.includes(',') && coordStr !== "abc, xyz") {
        const partes = coordStr.split(',');
        coordenadasObj = { 
            latitud: parseFloat(partes[0].trim()), 
            longitud: parseFloat(partes[1].trim()) 
        };
    } else if (coordStr === "abc, xyz") {
        // Al mandar esto, el backend recibirá algo que no puede mapear a Point
        // y saltará la validación de "Las coordenadas son inválidas"
        coordenadasObj = null; 
    }

    // 3. Armamos el objeto EXACTAMENTE como lo espera tu CentroAtencionPresenter
    // Incluimos el ID dentro del cuerpo
    const centroActualizado = {
        id: idCentro,
        nombre: datosNuevos.Nombre === "" ? null : datosNuevos.Nombre,
        direccion: datosNuevos.Dirección === "" ? null : datosNuevos.Dirección,
        localidad: datosNuevos.Localidad === "" ? null : datosNuevos.Localidad,
        provincia: datosNuevos.Provincia === "" ? null : datosNuevos.Provincia,
        telefono: telefonoOriginal,
        coordenadas: coordenadasObj
    };

    // 4. Hacemos la petición PUT a la URL base, tal como lo hiciste en Postman
    this.lastResponse = request('PUT', 'http://backend:8080/centros', {
        json: centroActualizado
    });
});