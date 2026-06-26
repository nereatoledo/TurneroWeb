package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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

  @Lazy
  @Autowired
  private PacienteValidator pacienteValidator;

  public List<Paciente> findAll() {
    List<Paciente> pacientes = new ArrayList<>();
    pacienteRepository.findAll().forEach(pacientes::add);
    return pacientes;
  }

  public Paciente findById(int id) {
    return pacienteRepository.findById(id).orElse(null);
  }

  public Paciente findByDni(String dni) {
    return pacienteRepository.findByDni(dni);
  }

  @Transactional
  public Paciente crearPaciente(Paciente paciente) {
    pacienteValidator.checkFormat(paciente);
    pacienteValidator.checkCreate(paciente);

    if (paciente.getObraSocial() != null && paciente.getObraSocial().getNombre() != null) {
      obraSocialRepository
          .findByNombre(paciente.getObraSocial().getNombre().trim())
          .ifPresent(paciente::setObraSocial);
    }

    paciente.setUsername(paciente.getDni());
    return pacienteRepository.save(paciente);
  }
}