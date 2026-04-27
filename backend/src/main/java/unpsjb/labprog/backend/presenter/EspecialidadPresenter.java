package unpsjb.labprog.backend.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.EspecialidadService;
import unpsjb.labprog.backend.model.Especialidad;

@RestController
@RequestMapping("especialidades")
public class EspecialidadPresenter {

    @Autowired
    EspecialidadService service;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> crearEspecialidad(@RequestBody Especialidad especialidad) {

        // 1. Validar que no haya campos vacíos
        if (especialidad.getNombre() == null || especialidad.getNombre().trim().isEmpty()) {
            return Response.response(HttpStatus.BAD_REQUEST, "El nombre de la especialidad es requerido.", null);
        }
        if (especialidad.getDescripcion() == null || especialidad.getDescripcion().trim().isEmpty()) {
            return Response.response(HttpStatus.BAD_REQUEST, "La descripción de la especialidad es obligatoria", null);
        }

        // 2. Validar que el nombre no exista (Evitar duplicados)
        if (service.existeNombre(especialidad.getNombre())) {
            return Response.response(HttpStatus.CONFLICT, "El nombre de la especialidad ya está en uso", null);
        }

        // 3. Guardar en la base de datos
        Especialidad especialidadGuardada = service.save(especialidad);
        return Response.response(HttpStatus.OK, "Especialidad creada con exitosamente", especialidadGuardada);
    }

    @RequestMapping(value = "/search/{term}", method = RequestMethod.GET)
    public ResponseEntity<Object> search(@PathVariable("term") String term) {
        return Response.ok(service.search(term));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> findAll() {
        return Response.ok(service.findAll());
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable("id") int id) {
        Especialidad especialidad = service.findById(id);

        if (especialidad != null) {
            return Response.ok(especialidad);
        } else {
            return Response.notFound("Especialidad con id " + id + " no encontrada.");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        Especialidad especialidad = service.findById(id);

        if (especialidad != null) {
            service.delete(id);
            return Response.response(HttpStatus.OK, "Especialidad eliminada exitosamente", null);
        } else {
            return Response.notFound("No se pudo eliminar: Especialidad con id " + id + " no encontrada.");
        }
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Object> findByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Response.ok(service.findByPage(page, size));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody Especialidad especialidadActualizada) {

        if (especialidadActualizada.getId() <= 0) {
            return Response.error(especialidadActualizada,
                    "Debe especificar un id válido para poder modificar una especialidad.");
        }

        String errorCampos = validarCamposObligatorios(especialidadActualizada);
        if (errorCampos != null) {
            return Response.response(HttpStatus.BAD_REQUEST, errorCampos, null);
        }

        Especialidad especialidadExistente = service.findById(especialidadActualizada.getId());
        if (especialidadExistente == null) {
            return Response.notFound("Especialidad id " + especialidadActualizada.getId() + " no encontrada.");
        }

        if (!especialidadExistente.getNombre().equalsIgnoreCase(especialidadActualizada.getNombre())) {
            if (service.existeNombre(especialidadActualizada.getNombre())) {
                return Response.response(HttpStatus.CONFLICT, "Ya existe otra especialidad con ese nombre.", null);
            }
        }

        especialidadExistente.setNombre(especialidadActualizada.getNombre());
        especialidadExistente.setDescripcion(especialidadActualizada.getDescripcion());

        service.save(especialidadExistente);

        return Response.response(HttpStatus.OK, "Especialidad modificada con éxito.", especialidadExistente);
    }

        private String validarCamposObligatorios(Especialidad e) {
        if (e.getNombre() == null || e.getNombre().trim().isEmpty()) {
            return "El nombre de la especialidad es requerido.";
        }
        if (e.getDescripcion() == null || e.getDescripcion().trim().isEmpty()) {
            return "La descripción de la especialidad es requerida.";
        }
        return null;
    }
}