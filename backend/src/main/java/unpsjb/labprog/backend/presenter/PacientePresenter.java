package unpsjb.labprog.backend.presenter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.PacienteRepository;
import unpsjb.labprog.backend.business.ObraSocialRepository;
import unpsjb.labprog.backend.model.Paciente;
import unpsjb.labprog.backend.model.ObraSocial;

@RestController
@CrossOrigin
@RequestMapping("pacientes")
public class PacientePresenter {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ObraSocialRepository obraSocialRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> findAll() {
        List<Paciente> pacientes = new ArrayList<>();
        pacienteRepository.findAll().forEach(pacientes::add);
        
        return Response.ok(pacientes, "Pacientes recuperados correctamente");
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> crearPaciente(@RequestBody Map<String, Object> payload) {
        String nombre = (String) payload.get("nombre");
        String apellido = (String) payload.get("apellido");
        String dni = (String) payload.get("dni");
        String fechaNacimientoStr = (String) payload.get("fechaNacimiento");
        Object obraSocialObj = payload.get("obraSocial");

        if (nombre == null || nombre.trim().isEmpty()) {
            return Response.response(HttpStatus.CONFLICT, "El Nombre es obligatorio", null);
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            return Response.response(HttpStatus.CONFLICT, "El apellido es obligatorio", null);
        }
        if (dni == null || dni.toString().trim().isEmpty()) {
            return Response.response(HttpStatus.CONFLICT, "El dni es obligatorio", null);
        }
        
        String dniStr = dni.toString().trim();
        if (!dniStr.matches("^[0-9]+$")) {
            return Response.response(HttpStatus.CONFLICT, "dni incorrecto, débe contener sólo números", null);
        }

        if (pacienteRepository.existsByDni(dniStr)) {
            return Response.response(HttpStatus.CONFLICT, "El dni ya existe en el sistema", null);
        }

        if (fechaNacimientoStr == null || fechaNacimientoStr.trim().isEmpty()) {
            return Response.response(HttpStatus.CONFLICT, "La fecha de nacimiento es obligatoria", null);
        }

        LocalDate fechaNac;
        try {
            fechaNac = LocalDate.parse(fechaNacimientoStr.trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            return Response.response(HttpStatus.CONFLICT, "La fecha de nacimiento es obligatoria", null);
        }

        Paciente paciente = new Paciente();
        paciente.setNombre(nombre.trim());
        paciente.setApellido(apellido.trim());
        paciente.setDni(dniStr);
        paciente.setFechaNacimiento(fechaNac);
        paciente.setUsername(dniStr);

        if (obraSocialObj != null) {
            String osNombre = null;
            if (obraSocialObj instanceof Map) {
                osNombre = (String) ((Map<?, ?>) obraSocialObj).get("nombre");
            } else if (obraSocialObj instanceof String) {
                osNombre = (String) obraSocialObj;
            }
            if (osNombre != null && !osNombre.trim().isEmpty()) {
                Optional<ObraSocial> os = obraSocialRepository.findByNombre(osNombre.trim());
                if (os.isPresent()) {
                    paciente.setObraSocial(os.get());
                }
            }
        }

        Paciente guardado = pacienteRepository.save(paciente);
        return Response.response(HttpStatus.OK, "Paciente ingresado correctamente", guardado);
    }
}