package unpsjb.labprog.backend.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.ConsultorioService;
import unpsjb.labprog.backend.model.Consultorio;

@RestController
@RequestMapping("consultorios")
public class ConsultorioPresenter {

    @Autowired
    ConsultorioService service;

    // endoints púbicos
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> crearConsultorio(@RequestBody Consultorio consultorio) {

        String errorCampos = validarCampos(consultorio);
        if (errorCampos != null) {
            return Response.response(HttpStatus.BAD_REQUEST, errorCampos, null);
        }

        if (service.existeNumero(consultorio.getNumero())) {
            return Response.response(HttpStatus.CONFLICT, "Error: El número de consultorio ya está registrado", null);
        }

        Consultorio consultorioGuardado = service.save(consultorio);
        return Response.response(HttpStatus.OK, "Consultorio creado", consultorioGuardado);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody Consultorio centroActualizado) {

        if (centroActualizado.getId() <= 0) {
            return Response.error(centroActualizado,
                    "Debe especificar un id válido para poder modificar un Centro de Atención.");
        }

        String errorCampos = validarCampos(centroActualizado);
        if (errorCampos != null) {
            return Response.response(HttpStatus.BAD_REQUEST, errorCampos, null);
        }

        Consultorio centroExistente = service.findById(centroActualizado.getId());
        if (centroExistente == null) {
            return Response.error(centroActualizado,
                    "Debe especificar un id válido para poder modificar un Centro de Atención.");
        }

        mapearDatos(centroExistente, centroActualizado);
        service.save(centroExistente);

        return Response.response(HttpStatus.OK, "Centro de atención modificado", centroExistente);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> findAll() {
        return Response.ok(service.findAll());
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable("id") int id) {
        Consultorio aCentroOrNull = service.findById(id);
        return (aCentroOrNull != null) ? Response.ok(aCentroOrNull)
                : Response.notFound("Centro de Atención id " + id + " no encontrado.");
    }

    @RequestMapping(value = "/search/{term}", method = RequestMethod.GET)
    public ResponseEntity<Object> search(@PathVariable("term") String term) {
        return Response.ok(service.search(term));
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Object> findByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Response.ok(service.findByPage(page, size));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        service.delete(id);
        return Response.ok("Centro De Atención borrado con éxito.", "Centro De Atención borrado con éxito.");
    }

    // métodos privados
    private String validarCampos(Consultorio c) {
        if (c.getNombre() == null || c.getNombre().trim().isEmpty())
            return "Error: El nombre del consultorio es obligatorio";
        if (!c.getNombre().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            return "Error: El nombre del consultorio contiene caracteres no permitidos";
        }
        return null;
    }

    private void mapearDatos(Consultorio existente, Consultorio nuevo) {
        existente.setNombre(nuevo.getNombre());
    }
}