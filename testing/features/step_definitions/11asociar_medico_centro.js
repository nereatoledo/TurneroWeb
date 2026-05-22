const { When } = require('@cucumber/cucumber');
const request = require('sync-request');

const BASE_URL = 'http://backend:8080';

When('el administrador asocia el médico con {string}, {string}, {int}, {string} y {string} al centro de atención {string}', function (nombre, apellido, dni, matricula, especialidad, centroDeAtencion) {
    
        try {
        let resEsp = request('GET', `${BASE_URL}/especialidades`, { throw: false });
        let especialidades = resEsp.statusCode === 200 ? JSON.parse(resEsp.getBody('utf8')).data || [] : [];
        let espObj = especialidades.find(e => e.nombre === especialidad);

        if (!espObj) {
            let postEsp = request('POST', `${BASE_URL}/especialidades`, { json: { nombre: especialidad }, throw: false });
            if (postEsp.statusCode === 200 || postEsp.statusCode === 201) {
                let parsed = JSON.parse(postEsp.getBody('utf8'));
                espObj = parsed.data || parsed;
            }
        }

        if (espObj) {
            request('POST', `${BASE_URL}/medicos`, {
                json: {
                    nombre: nombre,
                    apellido: apellido,
                    dni: dni.toString(),
                    matricula: matricula,
                    especialidad: espObj 
                },
                throw: false
            });
        }
    } catch (error) {
        console.log("Aviso en preparación:", error.message);
    }

    const payload = {
        centro_de_atencion: centroDeAtencion,
        dni: dni.toString(),
        matrícula: matricula 
    };

    this.lastResponse = request('POST', `${BASE_URL}/centros/asociar-medico`, {
        json: payload,
        throw: false
    });
});

When('el administrador desasocia el médico con id {int} del centro de atención {string}', function (idMedico, centroDeAtencion) {
    const payload = {
        centro_de_atencion: centroDeAtencion,
        id_medico: idMedico.toString()
    };

    this.lastResponse = request('DELETE', `${BASE_URL}/centros/desasociar-medico`, {
        json: payload,
        throw: false
    });
});