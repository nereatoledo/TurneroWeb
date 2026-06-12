package unpsjb.labprog.backend.presenter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.PacienteRepository;
import unpsjb.labprog.backend.model.Paciente;

@RestController
@RequestMapping("pacientes")
public class PacientePresenter {

    @Autowired
    private PacienteRepository pacienteRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> findAll() {
        List<Paciente> pacientes = new ArrayList<>();
        pacienteRepository.findAll().forEach(pacientes::add);
        
        return Response.ok(pacientes, "Pacientes recuperados correctamente");
    }
}