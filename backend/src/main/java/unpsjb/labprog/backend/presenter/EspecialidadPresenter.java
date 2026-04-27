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
        if (especialidad.getNombre() == null || especialidad.getNombre().trim().isEmpty()) {
            return Response.response(HttpStatus.BAD_REQUEST, "El nombre de la especialidad es requerido.", null);
        }
        if (especialidad.getDescripcion() == null || especialidad.getDescripcion().trim().isEmpty()) {
            return Response.response(HttpStatus.CONFLICT, "La descripción de la especialidad es obligatoria", null);
        }
        if (service.existeNombre(especialidad.getNombre())) {
            return Response.response(HttpStatus.CONFLICT, "Ya existe una especialidad con ese nombre", null);
        }

        Especialidad guardada = service.save(especialidad);
        return Response.response(HttpStatus.OK, "Especialidad creada exitosamente", guardada);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> findAll() {
        return Response.ok(service.findAll(), "especialidades recuperadas correctamente");
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody Especialidad especialidadActualizada) {
        if (especialidadActualizada.getId() <= 0) {
            return Response.error(especialidadActualizada, "Debe especificar un id válido.");
        }
        Especialidad existente = service.findById(especialidadActualizada.getId());
        if (existente == null) return Response.notFound("Especialidad no encontrada.");

        if (!existente.getNombre().equalsIgnoreCase(especialidadActualizada.getNombre())) {
            if (service.existeNombre(especialidadActualizada.getNombre())) {
                return Response.response(HttpStatus.CONFLICT, "El nombre de la especialidad ya está en uso", null);
            }
        }

        existente.setNombre(especialidadActualizada.getNombre());
        existente.setDescripcion(especialidadActualizada.getDescripcion());
        service.save(existente);
        return Response.response(HttpStatus.OK, "Especialidad editada exitosamente", existente);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        Especialidad e = service.findById(id);
        if (e != null) {
            service.delete(id);
            return Response.response(HttpStatus.OK, "Especialidad eliminada exitosamente", null);
        }
        return Response.notFound("No se pudo eliminar.");
    }

    @RequestMapping(value = "/search/{term}", method = RequestMethod.GET)
    public ResponseEntity<Object> search(@PathVariable("term") String term) {
        return Response.ok(service.search(term));
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable("id") int id) {
        Especialidad e = service.findById(id);
        return (e != null) ? Response.ok(e) : Response.notFound("Especialidad no encontrada.");
    }
}