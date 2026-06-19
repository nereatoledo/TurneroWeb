package unpsjb.labprog.backend.business;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.labprog.backend.model.Paciente;
import unpsjb.labprog.backend.validations.PacienteValidator;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ObraSocialRepository obraSocialRepository;

    @Autowired
    private PacienteValidator pacienteValidator;

    public List<Paciente> findAll() {
        List<Paciente> pacientes = new ArrayList<>();
        pacienteRepository.findAll().forEach(pacientes::add);
        return pacientes;
    }

    @Transactional
    public Paciente crearPaciente(Paciente paciente) {
        pacienteValidator.checkCreate(paciente);

        if (paciente.getObraSocial() != null && paciente.getObraSocial().getNombre() != null) {
            obraSocialRepository.findByNombre(paciente.getObraSocial().getNombre().trim())
                    .ifPresent(paciente::setObraSocial);
        }

        paciente.setUsername(paciente.getDni());
        return pacienteRepository.save(paciente);
    }
}
