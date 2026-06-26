package unpsjb.labprog.backend.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unpsjb.labprog.backend.business.MedicoService;
import unpsjb.labprog.backend.exceptions.AtributoDuplicadoException;
import unpsjb.labprog.backend.exceptions.AtributoInvalidoException;
import unpsjb.labprog.backend.exceptions.EntidadNoEncontradaException;
import unpsjb.labprog.backend.model.Medico;

@Component
public class MedicoValidator implements Validator<Medico> {
  @Autowired MedicoService service;
  @Autowired EspecialidadValidator especialidadValidator;

  @Override
  public void checkFormat(Medico aMedico) {
    if (aMedico.getNombre() == null || aMedico.getNombre().trim().equals(""))
      throw new AtributoInvalidoException("El Nombre es obligatorio");
    aMedico.setNombre(aMedico.getNombre().trim());
    if (aMedico.getApellido() == null || aMedico.getApellido().trim().equals(""))
      throw new AtributoInvalidoException("El apellido es obligatorio");
    aMedico.setApellido(aMedico.getApellido().trim());
    if (aMedico.getDni() == null || aMedico.getDni().trim().equals(""))
      throw new AtributoInvalidoException("El dni es obligatorio");
    aMedico.setDni(aMedico.getDni().trim());
    try {
      Integer.parseInt(aMedico.getDni());
    } catch (NumberFormatException e) {
      throw new AtributoInvalidoException("dni incorrecto, débe contener sólo números");
    }
    if (Integer.parseInt(aMedico.getDni()) < 0)
      throw new AtributoInvalidoException("dni incorrecto, debe ser positivo");
    if (aMedico.getMatricula() == null || aMedico.getMatricula().trim().equals(""))
      throw new AtributoInvalidoException("La matrícula es obligatoria");
    aMedico.setMatricula(aMedico.getMatricula().trim());
    if (aMedico.getEspecialidad() == null)
      throw new AtributoInvalidoException("La especialidad NO existe");
    especialidadValidator.checkExists(aMedico.getEspecialidad());
  }

  @Override
  public void checkUpdate(Medico aMedico) {
    Medico anotherMedico = service.findByDni(aMedico.getDni());
    if (anotherMedico != null && anotherMedico.getId() != aMedico.getId())
      throw new AtributoDuplicadoException("El dni ya existe en el sistema");
    anotherMedico = service.findByMatricula(aMedico.getMatricula());
    if (anotherMedico != null && anotherMedico.getId() != aMedico.getId())
      throw new AtributoDuplicadoException("La Matrícula ya existe en el sistema");
  }

  @Override
  public void checkCreate(Medico aMedico) {
    if (service.findByDni(aMedico.getDni()) != null)
      throw new AtributoDuplicadoException("El dni ya existe en el sistema");
    if (service.findByMatricula(aMedico.getMatricula()) != null)
      throw new AtributoDuplicadoException("La Matrícula ya existe en el sistema");
  }

  @Override
  public void checkExists(Medico aMedico) {
    if (aMedico.getId() <= 0) throw new AtributoInvalidoException("Debe especificar un ID válido");
    if (service.findById(aMedico.getId()) == null)
      throw new EntidadNoEncontradaException("No existe el médico");
  }
}
