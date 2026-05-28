const { Given, When, Then } = require('@cucumber/cucumber');
const assert = require('assert');
const request = require('sync-request');

const backendUrl = 'http://backend:8080';

let agendaDTO = {
    nombre: "Agenda de Atencion Standard",
    descripcion: "Creado desde Cucumber",
    horaInicio: "",
    horaFin: "",
    fechaInicio: "", 
    fechaFin: "",    
    idConsultorio: null,
    idMedico: null
};

let respuestaServidor;

Given('que el administrador configura la agenda del {string}', function (nombreConsultorio) {
    const res = request('GET', `${backendUrl}/consultorios`);
    const consultorios = JSON.parse(res.getBody('utf8')).data;
    const consultorio = consultorios.find(c => c.nombre === nombreConsultorio);
    
    agendaDTO.idConsultorio = consultorio ? consultorio.id : 1;
});

Given('define el horario de atención de {string} a {string} de {word} a {word}', function (horaInicio, horaFin, diaDesde, diaHasta) {
    agendaDTO.horaInicio = horaInicio;
    agendaDTO.horaFin = horaFin;

    function obtenerFechaProximaSemana(nombreDia) {
        const diasOffset = {
            "lunes": 0, "martes": 1, "miércoles": 2, "miercoles": 2,
            "jueves": 3, "viernes": 4, "sábado": 5, "sabado": 5, "domingo": 6
        };

        let fecha = new Date();
        
        let diasParaLunes = (1 - fecha.getDay() + 7) % 7;
        if (diasParaLunes === 0) diasParaLunes = 7; 
        fecha.setDate(fecha.getDate() + diasParaLunes);

        fecha.setDate(fecha.getDate() + diasOffset[nombreDia.toLowerCase()]);

        return fecha.toISOString().split('T')[0];
    }

    agendaDTO.fechaInicio = obtenerFechaProximaSemana(diaDesde);
    agendaDTO.fechaFin = obtenerFechaProximaSemana(diaHasta);
});

Given('asigna al Dr. {string} con especialidad {string}', function (nombreMedico, especialidad) {
    const res = request('GET', `${backendUrl}/centros/medicos`); 
    const centrosData = JSON.parse(res.getBody('utf8')).data;
    
    let medicoEncontrado = null;
    
    for (let centro of centrosData) {
        const med = centro.medicos.find(m => {
            const nombreCompleto = `${m.nombre} ${m.apellido}`;
            return m.nombre === nombreMedico || m.apellido === nombreMedico || nombreCompleto.includes(nombreMedico);
        });
        
        if (med && med.especialidad === especialidad) {
            medicoEncontrado = med;
            break;
        }
    }
    
    agendaDTO.idMedico = medicoEncontrado ? medicoEncontrado.id : 1;
});

When('guarda la configuración', function () {
    try {
        respuestaServidor = request('POST', `${backendUrl}/esquemas-turnos`, {
            json: agendaDTO
        });
    } catch (error) {
        console.log("=== ERROR DE CONEXIÓN O REQUEST ===");
        console.log(error);
        respuestaServidor = error;
    }
});

Then('el sistema confirma la creación de la agenda con código {int}', function (codigoEsperado) {
    if (respuestaServidor.statusCode !== 200) {
        console.log("\n=== ERROR DETALLADO DEL SERVIDOR (SPRING BOOT) ===");
        console.log(respuestaServidor.getBody('utf8'));
        console.log("===================================================\n");
    }
    assert.strictEqual(respuestaServidor.statusCode, codigoEsperado);
});