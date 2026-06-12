package unpsjb.labprog.backend.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unpsjb.labprog.backend.business.PacienteRepository;
import unpsjb.labprog.backend.business.ObraSocialRepository;
import unpsjb.labprog.backend.model.Paciente;
import unpsjb.labprog.backend.model.ObraSocial;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("auth")
public class LoginPresenter {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ObraSocialRepository obraSocialRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String username = credenciales.get("username");

        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El nombre de usuario es requerido"));
        }

        if ("admin".equalsIgnoreCase(username.trim())) {
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Login exitoso como Administrador",
                    "username", "admin",
                    "rol", "ADMIN"));
        }

        Optional<Paciente> paciente = pacienteRepository.findByUsername(username.trim());
        if (paciente.isPresent()) {
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Login exitoso",
                    "username", paciente.get().getUsername(),
                    "rol", "PACIENTE",
                    "id", paciente.get().getId()));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Paciente no encontrado"));
    }

    @RequestMapping(value = "/obras-sociales", method = RequestMethod.GET)
    public ResponseEntity<List<ObraSocial>> listarObrasSociales() {
        List<ObraSocial> obrasSociales = new ArrayList<>();
        obraSocialRepository.findAll().forEach(obrasSociales::add);
        return ResponseEntity.ok(obrasSociales);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registrarPaciente(@RequestBody Map<String, Object> datos) {
        String username = (String) datos.get("username");
        String nombre = (String) datos.get("nombre");
        String apellido = (String) datos.get("apellido");
        String dni = (String) datos.get("dni");
        String fechaNacStr = (String) datos.get("fechaNacimiento");
        Integer obraSocialId = (Integer) datos.get("obraSocialId");

        if (username == null || username.trim().isEmpty() ||
                nombre == null || nombre.trim().isEmpty() ||
                apellido == null || apellido.trim().isEmpty() ||
                dni == null || dni.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Todos los campos obligatorios deben completarse"));
        }

        username = username.trim();
        dni = dni.trim();

        if (pacienteRepository.existsByUsername(username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "El nombre de usuario '" + username + "' ya está registrado."));
        }

        if (pacienteRepository.existsByDni(dni)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Ya existe un paciente con el DNI " + dni));
        }

        Paciente nuevoPaciente = new Paciente();
        nuevoPaciente.setUsername(username);
        nuevoPaciente.setNombre(nombre.trim());
        nuevoPaciente.setApellido(apellido.trim());
        nuevoPaciente.setDni(dni);

        try {
            if (fechaNacStr != null && !fechaNacStr.isEmpty()) {
                java.time.LocalDate fecha = java.time.LocalDate.parse(fechaNacStr);
                nuevoPaciente.setFechaNacimiento(fecha);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Formato de fecha inválido"));
        }

        if (obraSocialId != null) {
            Optional<ObraSocial> os = obraSocialRepository.findById(obraSocialId);
            os.ifPresent(nuevoPaciente::setObraSocial);
        }

        pacienteRepository.save(nuevoPaciente);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensaje", "Paciente creado con éxito", "username", username));
    }

    @RequestMapping(value = "/paciente/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPaciente(@PathVariable Integer id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        if (paciente.isPresent()) {
            return ResponseEntity.ok(paciente.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Paciente no encontrado"));
    }

    @RequestMapping(value = "/paciente/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePaciente(@PathVariable Integer id, @RequestBody Map<String, Object> datos) {
        Optional<Paciente> pacienteOpt = pacienteRepository.findById(id);
        if (pacienteOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Paciente no encontrado"));
        }

        Paciente paciente = pacienteOpt.get();
        String username = (String) datos.get("username");
        Integer obraSocialId = (Integer) datos.get("obraSocialId");

        if (username != null && !username.trim().isEmpty()) {
            username = username.trim();
            if (!username.equals(paciente.getUsername()) && pacienteRepository.existsByUsername(username)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("error", "El nombre de usuario '" + username + "' ya está en uso."));
            }
            paciente.setUsername(username);
        }

        if (obraSocialId != null) {
            Optional<ObraSocial> os = obraSocialRepository.findById(obraSocialId);
            os.ifPresent(paciente::setObraSocial);
        } else {
            paciente.setObraSocial(null);
        }

        pacienteRepository.save(paciente);
        return ResponseEntity.ok(Map.of("mensaje", "Paciente actualizado con éxito", "username", paciente.getUsername()));
    }
}