package unpsjb.labprog.backend.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unpsjb.labprog.backend.business.EspecialidadService;
import unpsjb.labprog.backend.exceptions.AtributoDuplicadoException;
import unpsjb.labprog.backend.exceptions.AtributoInvalidoException;
import unpsjb.labprog.backend.exceptions.EntidadNoEncontradaException;
import unpsjb.labprog.backend.model.Especialidad;

@Component
public class EspecialidadValidator implements Validator<Especialidad> {
  @Autowired private EspecialidadService service;

  @Override
  public void checkFormat(Especialidad aEspecialidad) {
    if (aEspecialidad == null) throw new EntidadNoEncontradaException();
    if (aEspecialidad.getNombre() == null || aEspecialidad.getNombre().equals(""))
      throw new AtributoDuplicadoException("El nombre de la especialidad es obligatoria");
    if (aEspecialidad.getDescripcion() == null)
      throw new AtributoDuplicadoException("La descripción de la especialidad es obligatoria");
    if (aEspecialidad.getDescripcion().equals("")
        || aEspecialidad.getDescripcion().trim().equals(""))
      throw new AtributoDuplicadoException("La descripción de la especialidad es obligatoria");
  }

  @Override
  public void checkUpdate(Especialidad aEspecialidad) {
    if (aEspecialidad.getId() <= 0)
      throw new AtributoInvalidoException("Debe especificar un ID válido");
    if (service.findById(aEspecialidad.getId()) == null)
      throw new EntidadNoEncontradaException("No existe la especialidad");
    Especialidad otraEspecialidad = service.findByNombre(aEspecialidad.getNombre());
    boolean nombreDuplicado = false;
    if (otraEspecialidad != null)
      nombreDuplicado = otraEspecialidad.getId() != aEspecialidad.getId();
    if (nombreDuplicado)
      throw new AtributoDuplicadoException("El nombre de la especialidad ya está en uso");
  }

  @Override
  public void checkCreate(Especialidad aEspecialidad) {
    if (aEspecialidad.getId() != 0)
      throw new AtributoInvalidoException("No se puede determinar un ID al crear");
    boolean nombreDuplicado = service.findByNombre(aEspecialidad.getNombre()) != null;
    if (nombreDuplicado)
      throw new AtributoDuplicadoException("Ya existe una especialidad con ese nombre");
  }

  @Override
  public void checkExists(Especialidad aEspecialidad) {
    if (aEspecialidad.getId() <= 0)
      throw new AtributoInvalidoException("Debe especificar un ID válido");
    if (service.findById(aEspecialidad.getId()) == null)
      throw new EntidadNoEncontradaException("No existe la especialidad");
  }
}
