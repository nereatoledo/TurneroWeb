package unpsjb.labprog.backend.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.ConsultorioService;
import unpsjb.labprog.backend.business.CentroAtencionService;
import unpsjb.labprog.backend.model.Consultorio;
import unpsjb.labprog.backend.model.CentroAtencion;

@RestController
@RequestMapping("consultorios")
public class ConsultorioPresenter {

    @Autowired
    ConsultorioService service;

    @Autowired
    CentroAtencionService centroService;

    @RequestMapping(value = "/centro/{idCentro}", method = RequestMethod.POST)
    public ResponseEntity<Object> crearConsultorio(
            @PathVariable("idCentro") Integer idCentro,
            @RequestBody Consultorio consultorio) {

        CentroAtencion centro = centroService.findById(idCentro);
        if (centro == null) {
            return Response.response(HttpStatus.NOT_FOUND, "Error: El centro de atención especificado no existe", null);
        }

        if (consultorio.getNumero() == null) {
            return Response.response(HttpStatus.CONFLICT, "Error: Debe especificar un número de consultorio válido",
                    null);
        }
        if (consultorio.getNombre() == null || consultorio.getNombre().trim().isEmpty()) {
            return Response.response(HttpStatus.CONFLICT, "Error: El nombre del consultorio es obligatorio", null);
        }
        if (!consultorio.getNombre().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9 ]+$")) {
            return Response.response(HttpStatus.CONFLICT,
                    "Error: El nombre del consultorio contiene caracteres no permitidos", null);
        }

        if (service.existeConsultorioEnCentro(idCentro, consultorio.getNumero())) {
            return Response.response(HttpStatus.CONFLICT, "Error: El número de consultorio ya está registrado", null);
        }
        if (service.existeNombreEnCentro(idCentro, consultorio.getNombre())) {
            return Response.response(HttpStatus.CONFLICT, "Error: El nombre del consultorio ya está registrado", null);
        }

        Consultorio consultorioGuardado = service.save(consultorio);

        centro.agregarConsultorio(consultorioGuardado);
        centroService.save(centro);

        return Response.response(HttpStatus.OK, "Consultorio creado exitosamente", consultorioGuardado);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody Consultorio consultorioActualizado) {
        if (consultorioActualizado.getId() <= 0) {
            return Response.error(consultorioActualizado,
                    "Debe especificar un id válido para poder modificar un consultorio.");
        }

        String errorCampos = validarCamposParaEdicion(consultorioActualizado);
        if (errorCampos != null) {
            return Response.response(HttpStatus.BAD_REQUEST, errorCampos, null);
        }

        Consultorio existente = service.findById(consultorioActualizado.getId());
        if (existente == null) {
            return Response.notFound("Consultorio id " + consultorioActualizado.getId() + " no encontrado.");
        }

        if (!existente.getNumero().equals(consultorioActualizado.getNumero())) {
            CentroAtencion centro = centroService.findCentroByConsultorioId(existente.getId());
            if (centro != null
                    && service.existeConsultorioEnCentro(centro.getId(), consultorioActualizado.getNumero())) {
                return Response.response(HttpStatus.CONFLICT,
                        "Error: El número de consultorio ya está registrado en este centro", null);
            }
        }

        mapearDatos(existente, consultorioActualizado);
        service.save(existente);

        return Response.response(HttpStatus.OK, "Consultorio modificado con éxito", existente);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> findAll() {
        return Response.ok(service.findAll());
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable("id") int id) {
        Consultorio aConsultorioOrNull = service.findById(id);
        return (aConsultorioOrNull != null) ? Response.ok(aConsultorioOrNull)
                : Response.notFound("Consultorio id " + id + " no encontrado.");
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
        return Response.ok("Consultorio borrado con éxito.", "Consultorio borrado con éxito.");
    }

    private String validarCamposParaEdicion(Consultorio c) {
        if (c.getNombre() == null || c.getNombre().trim().isEmpty())
            return "Error: El nombre del consultorio es obligatorio";
        if (!c.getNombre().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9 ]+$")) {
            return "Error: El nombre del consultorio contiene caracteres no permitidos";
        }
        return null;
    }

    private void mapearDatos(Consultorio existente, Consultorio nuevo) {
        existente.setNombre(nuevo.getNombre());
        existente.setNumero(nuevo.getNumero());
    }
}