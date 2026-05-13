package unpsjb.labprog.backend.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.MedicoService;
import unpsjb.labprog.backend.business.EspecialidadService;
import unpsjb.labprog.backend.model.Medico;
import unpsjb.labprog.backend.model.Especialidad;

@RestController
@RequestMapping("medicos")
public class MedicoPresenter {

    @Autowired
    MedicoService service;

    @Autowired
    EspecialidadService especialidadService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> findAll() {
        return Response.ok(service.findAll(), "médicos recuperados correctamente");
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable("id") int id) {
        Medico medico = service.findById(id);
        return (medico != null) ? Response.ok(medico) : Response.notFound("Médico no encontrado");
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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody Medico medico) {
        
        if (medico.getNombre() == null || medico.getNombre().trim().isEmpty()) {
            return Response.response(HttpStatus.CONFLICT, "El Nombre es obligatorio", null);
        }
        if (medico.getApellido() == null || medico.getApellido().trim().isEmpty()) {
            return Response.response(HttpStatus.CONFLICT, "El apellido es obligatorio", null);
        }
        if (medico.getDni() == null || medico.getDni().trim().isEmpty()) {
            return Response.response(HttpStatus.CONFLICT, "El dni es obligatorio", null);
        }
        if (!medico.getDni().matches("\\d+")) {
            return Response.response(HttpStatus.CONFLICT, "dni incorrecto, débe contener sólo números", null);
        }
        if (medico.getMatricula() == null || medico.getMatricula().trim().isEmpty()) {
            return Response.response(HttpStatus.CONFLICT, "La matrícula es obligatoria", null);
        }

        if (medico.getEspecialidad() != null && medico.getEspecialidad().getNombre() != null) {
            Especialidad espDb = especialidadService.findByNombre(medico.getEspecialidad().getNombre());
            if (espDb == null) {
                return Response.response(HttpStatus.CONFLICT, "La especialidad NO existe", null);
            }
            medico.setEspecialidad(espDb);
        } else {
            return Response.response(HttpStatus.CONFLICT, "La especialidad NO existe", null);
        }
        
        if (service.findByDni(medico.getDni()) != null) {
            return Response.response(HttpStatus.CONFLICT, "El dni ya existe en el sistema", null);
        }
        if (service.findByMatricula(medico.getMatricula()) != null) {
            return Response.response(HttpStatus.CONFLICT, "La Matrícula ya existe en el sistema", null);
        }

        return Response.response(HttpStatus.OK, "Médico Ingresado Correctamente", service.save(medico));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody Medico medico) {
        return Response.ok(service.save(medico), "Médico actualizado correctamente");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        Medico medico = service.findById(id);
        if (medico != null) {
            service.delete(id);
            return Response.response(HttpStatus.OK, "Médico eliminado exitosamente", null);
        }
        return Response.notFound("No se pudo eliminar. Médico no encontrado.");
    }
}