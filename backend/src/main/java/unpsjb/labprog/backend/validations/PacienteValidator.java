package unpsjb.labprog.backend.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unpsjb.labprog.backend.business.PacienteService;
import unpsjb.labprog.backend.exceptions.AtributoDuplicadoException;
import unpsjb.labprog.backend.exceptions.AtributoInvalidoException;
import unpsjb.labprog.backend.model.Paciente;

@Component
public class PacienteValidator implements Validator<Paciente> {
  @Autowired PacienteService service;

  @Override
  public void checkFormat(Paciente aPaciente) {
    if (aPaciente.getNombre() == null || aPaciente.getNombre().trim().equals(""))
      throw new AtributoInvalidoException("El Nombre es obligatorio");
    if (aPaciente.getApellido() == null || aPaciente.getApellido().trim().equals(""))
      throw new AtributoInvalidoException("El apellido es obligatorio");
    if (aPaciente.getDni() == null || aPaciente.getDni().trim().equals(""))
      throw new AtributoInvalidoException("El dni es obligatorio");
    try {
      Integer.parseInt(aPaciente.getDni());
    } catch (NumberFormatException e) {
      throw new AtributoInvalidoException("dni incorrecto, débe contener sólo números");
    }
    if (Integer.parseInt(aPaciente.getDni()) < 0)
      throw new AtributoInvalidoException("dni incorrecto, no puede ser negativo");
    if (aPaciente.getFechaNacimiento() == null)
      throw new AtributoInvalidoException("La fecha de nacimiento es obligatoria");
  }

  @Override
  public void checkUpdate(Paciente aPaciente) {
    Paciente anotherPaciente = service.findById(aPaciente.getId());
    if (anotherPaciente == null) throw new AtributoInvalidoException("El paciente no existe");
    anotherPaciente = service.findByDni(aPaciente.getDni());
    if (anotherPaciente != null && aPaciente.getId() != anotherPaciente.getId())
      throw new AtributoInvalidoException("El dni ya existe en el sistema");
  }

  @Override
  public void checkCreate(Paciente aPaciente) {
    if (aPaciente.getId() != 0)
      throw new AtributoDuplicadoException("No se puede especificar un ID al crear un paciente");
    if (service.findByDni(aPaciente.getDni()) != null)
      throw new AtributoDuplicadoException("El dni ya existe en el sistema");
  }

  @Override
  public void checkExists(Paciente object) {}
}
