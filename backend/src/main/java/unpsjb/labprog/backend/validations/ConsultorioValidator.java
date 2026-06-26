package unpsjb.labprog.backend.validations;

import org.springframework.stereotype.Component;
import unpsjb.labprog.backend.exceptions.AtributoInvalidoException;
import unpsjb.labprog.backend.model.Consultorio;

@Component
public class ConsultorioValidator implements Validator<Consultorio> {
  @Override
  public void checkFormat(Consultorio aConsultorio) {
    if (aConsultorio.getNombre() == null || aConsultorio.getNombre().trim().equals(""))
      throw new AtributoInvalidoException("Error: El nombre del consultorio es obligatorio");
    if (!aConsultorio.getNombre().matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ0-9., ]+"))
      throw new AtributoInvalidoException(
          "Error: El nombre del consultorio contiene caracteres no permitidos");
    Integer numeroConsultorio = aConsultorio.getNumero();
    if (numeroConsultorio == null) {
      throw new AtributoInvalidoException(
          "Error: Debe especificar un número de consultorio válido");
    }
    if (numeroConsultorio <= 0)
      throw new AtributoInvalidoException(
          "Error: Debe especificar un número de consultorio válido");
  }

  @Override
  public void checkUpdate(Consultorio aConsultorio) {
    if (aConsultorio.getId() <= 0) throw new AtributoInvalidoException("ID Inválido");
  }

  @Override
  public void checkCreate(Consultorio aConsultorio) {
    if (aConsultorio.getId() != 0)
      throw new AtributoInvalidoException("No se puede determinar un ID al crear");
  }

  @Override
  public void checkExists(Consultorio object) {}
}
