package unpsjb.labprog.backend.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.EspecialidadService;
import unpsjb.labprog.backend.business.MedicoService;
import unpsjb.labprog.backend.model.Especialidad;
import unpsjb.labprog.backend.model.Medico;

@RestController
@RequestMapping("medicos")
public class MedicoPresenter {

    @Autowired
    MedicoService service;

    @Autowired
    EspecialidadService especialidadService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> crearMedico(@RequestBody Medico medico) {

        String errorCampos = validarCamposObligatorios(medico);
        if (errorCampos != null) {
            return Response.response(HttpStatus.CONFLICT, errorCampos, null);
        }

        if (medico.getEspecialidad() == null || medico.getEspecialidad().getNombre() == null) {
            return Response.response(HttpStatus.CONFLICT, "La especialidad NO existe", null);
        }

        Especialidad especialidadReal = especialidadService.findByNombre(medico.getEspecialidad().getNombre());

        if (especialidadReal == null) {
            return Response.response(HttpStatus.CONFLICT, "La especialidad NO existe", null);
        }

        medico.setEspecialidad(especialidadReal);

        try {
            Medico medicoGuardado = service.save(medico);

            Map<String, Object> respuestaExito = new HashMap<>();
            respuestaExito.put("id", medicoGuardado.getId());
            respuestaExito.put("nombre", medicoGuardado.getNombre());
            respuestaExito.put("apellido", medicoGuardado.getApellido());
            respuestaExito.put("dni", medicoGuardado.getDni());
            respuestaExito.put("matricula", medicoGuardado.getMatricula());
            respuestaExito.put("especialidad", especialidadReal.getNombre());

            return Response.response(HttpStatus.OK, "Médico Ingresado Correctamente", respuestaExito);

        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            String msj = e.getMostSpecificCause() != null ? e.getMostSpecificCause().getMessage().toLowerCase() : "";

            if (msj.contains("dni")) {
                return Response.response(HttpStatus.CONFLICT, "El dni ya existe en el sistema", null);
            } else if (msj.contains("matricula")) {
                return Response.response(HttpStatus.CONFLICT, "La Matrícula ya existe en el sistema", null);
            } else {
                return Response.response(HttpStatus.CONFLICT, "Error de integridad de datos al guardar", null);
            }

        } catch (Exception e) {
            return Response.response(HttpStatus.INTERNAL_SERVER_ERROR, "Error de integridad de datos.",
                    null);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> findAll() {
        List<Medico> medicosDB = service.findAll();
        List<Map<String, Object>> medicosMapeados = new ArrayList<>();

        for (Medico m : medicosDB) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", m.getId());
            map.put("nombre", m.getNombre());
            map.put("apellido", m.getApellido());
            map.put("dni", m.getDni());
            map.put("matricula", m.getMatricula());
            map.put("especialidad", m.getEspecialidad().getNombre());

            medicosMapeados.add(map);
        }
        return Response.response(HttpStatus.OK, "médicos recuperados correctamente", medicosMapeados);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody Medico medicoActualizado) {
        if (medicoActualizado.getId() <= 0) {
            return Response.error(medicoActualizado, "Debe especificar un id válido para poder modificar un médico.");
        }

        String errorCampos = validarCamposObligatorios(medicoActualizado);
        if (errorCampos != null) {
            return Response.response(HttpStatus.CONFLICT, errorCampos, null);
        }

        Medico existente = service.findById(medicoActualizado.getId());
        if (existente == null) {
            return Response.notFound("Médico id " + medicoActualizado.getId() + " no encontrado.");
        }

        Medico medicoConDni = service.findByDni(medicoActualizado.getDni());
        if (medicoConDni != null && medicoConDni.getId() != existente.getId()) {
            return Response.response(HttpStatus.CONFLICT, "El dni ya existe en el sistema", null);
        }

        Medico medicoConMatricula = service.findByMatricula(medicoActualizado.getMatricula());
        if (medicoConMatricula != null && medicoConMatricula.getId() != existente.getId()) {
            return Response.response(HttpStatus.CONFLICT, "La Matrícula ya existe en el sistema", null);
        }

        if (medicoActualizado.getEspecialidad() == null || medicoActualizado.getEspecialidad().getNombre() == null) {
            return Response.response(HttpStatus.CONFLICT, "La especialidad NO existe", null);
        }

        Especialidad especialidadReal = especialidadService
                .findByNombre(medicoActualizado.getEspecialidad().getNombre());

        if (especialidadReal == null) {
            return Response.response(HttpStatus.CONFLICT, "La especialidad NO existe", null);
        }

        mapearDatos(existente, medicoActualizado);
        existente.setEspecialidad(especialidadReal);

        service.save(existente);
        return Response.response(HttpStatus.OK, "Médico modificado con éxito", existente);
    }

    private void mapearDatos(Medico existente, Medico nuevo) {
        existente.setNombre(nuevo.getNombre());
        existente.setApellido(nuevo.getApellido());
        existente.setDni(nuevo.getDni());
        existente.setMatricula(nuevo.getMatricula());
    }

    private String validarCamposObligatorios(Medico m) {
        if (m.getNombre() == null || m.getNombre().trim().isEmpty())
            return "El Nombre es obligatorio";
        if (m.getApellido() == null || m.getApellido().trim().isEmpty())
            return "El apellido es obligatorio";
        if (m.getDni() == null || m.getDni().trim().isEmpty())
            return "El dni es obligatorio";
        if (!m.getDni().matches("^[0-9]+$")) {
            return "dni incorrecto, débe contener sólo números";
        }
        if (m.getMatricula() == null || m.getMatricula().trim().isEmpty())
            return "La matrícula es obligatoria";

        return null;
    }
}