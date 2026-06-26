package unpsjb.labprog.backend.validations;

import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unpsjb.labprog.backend.exceptions.AtributoInvalidoException;
import unpsjb.labprog.backend.model.DisponibilidadMedico;
import unpsjb.labprog.backend.model.Horario;
import unpsjb.labprog.backend.model.Medico;
import unpsjb.labprog.backend.model.StaffMedico;

@Component
public class StaffMedicoValidator implements Validator<StaffMedico> {
  @Autowired MedicoValidator medicoValidator;

  @Override
  public void checkFormat(StaffMedico aStaffMedico) {
    medicoValidator.checkExists(aStaffMedico.getMedico());
    Medico aMedico = aStaffMedico.getMedico();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    for (DisponibilidadMedico aDisponibilidad : aStaffMedico.getDisponibilidad()) {
      if (!aDisponibilidad.getHoraInicio().isBefore(aDisponibilidad.getHoraFin())) {
        throw new AtributoInvalidoException(
            "No se puede cargar la disponibilidad de "
                + aMedico.getNombre()
                + " "
                + aMedico.getApellido()
                + " los "
                + aDisponibilidad.getDiaSemana()
                + " de "
                + aDisponibilidad.getHoraInicio().format(formatter)
                + " a "
                + aDisponibilidad.getHoraFin().format(formatter)
                + " porque el horario es inválido");
      }
      for (DisponibilidadMedico anotherDisponibilidad : aStaffMedico.getDisponibilidad()) {
        if (aDisponibilidad.getId() == anotherDisponibilidad.getId()) continue;
        if (aDisponibilidad.getDiaSemana().equals(anotherDisponibilidad.getDiaSemana())) {
          Horario h1 = new Horario(aDisponibilidad.getHoraInicio(), aDisponibilidad.getHoraFin());
          Horario h2 =
              new Horario(
                  anotherDisponibilidad.getHoraInicio(), anotherDisponibilidad.getHoraFin());
          if (h1.interseccion(h2) != null) {
            throw new AtributoInvalidoException(
                "No se puede cargar la disponibilidad de "
                    + aStaffMedico.getMedico().getNombre()
                    + " "
                    + aStaffMedico.getMedico().getApellido()
                    + " porque se encontró una superposición de disponibilidad el "
                    + aDisponibilidad.getDiaSemana());
          }
        }
      }
    }
  }

  @Override
  public void checkUpdate(StaffMedico object) {}

  @Override
  public void checkCreate(StaffMedico object) {}

  @Override
  public void checkExists(StaffMedico object) {}
}
