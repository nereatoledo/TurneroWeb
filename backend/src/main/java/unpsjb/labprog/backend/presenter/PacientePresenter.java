package unpsjb.labprog.backend.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.PacienteService;
import unpsjb.labprog.backend.model.Paciente;

@RestController
@CrossOrigin
@RequestMapping("pacientes")
public class PacientePresenter {

    @Autowired
    private PacienteService pacienteService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> findAll() {
        return Response.ok(pacienteService.findAll(), "Pacientes recuperados correctamente");
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> crearPaciente(@RequestBody Paciente paciente) {
        Paciente guardado = pacienteService.crearPaciente(paciente);
        return Response.response(HttpStatus.OK, "Paciente ingresado correctamente", guardado);
    }
}