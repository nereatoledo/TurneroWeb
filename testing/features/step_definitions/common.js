const { Then } = require('@cucumber/cucumber');
const assert = require('assert');

function obtenerIdSeguro(res) {
    let bodyStr = res.body.toString('utf8');
    let body = JSON.parse(bodyStr || '{}');
    if (body && body.data) {
        return body.data.id;
    } else {
        throw new Error(`Error en el backend: ${JSON.stringify(body)}`);
    }
}

module.exports = { obtenerIdSeguro };
Then('el sistema muestra un mensaje de error {string}', function (mensajeEsperado) {
    const res = this.respuestaServidor || this.respuestaAccion || this.respuestaCancelacion;
    assert.ok(res, 'No hay respuesta del servidor disponible en el contexto del escenario.');
    let bodyText;
    if (res.body) {
        bodyText = res.body.toString('utf8');
    } else if (res.response && res.response.body) {
        bodyText = res.response.body.toString('utf8');
    } else {
        bodyText = '{}';
    }
    let cuerpoRespuesta;
    try {
        cuerpoRespuesta = JSON.parse(bodyText);
    } catch (e) {
        assert.fail(`La respuesta del servidor no es JSON válido. Body: ${bodyText}`);
    }
    const msg = cuerpoRespuesta.message || cuerpoRespuesta.error;
    assert.ok(msg, `La respuesta no contiene propiedad 'message' ni 'error'. Body: ${bodyText}`);
    assert.strictEqual(msg, mensajeEsperado);
});
