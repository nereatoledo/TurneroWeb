package unpsjb.labprog.backend.validations;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unpsjb.labprog.backend.business.EsquemaTurnoService;
import unpsjb.labprog.backend.business.TurnoService;
import unpsjb.labprog.backend.exceptions.AtributoInvalidoException;
import unpsjb.labprog.backend.exceptions.HorarioIncompatibleException;
import unpsjb.labprog.backend.model.DisponibilidadMedico;
import unpsjb.labprog.backend.model.EsquemaTurno;
import unpsjb.labprog.backend.model.EstadoTurno;
import unpsjb.labprog.backend.model.Horario;
import unpsjb.labprog.backend.model.Turno;

@Component
public class EsquemaTurnoValidator implements Validator<EsquemaTurno> {
  @Autowired EsquemaTurnoService service;
  @Autowired TurnoService turnoService;

  @Override
  public void checkFormat(EsquemaTurno aEsquemaTurno) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    if (aEsquemaTurno.getStaffMedico().getDisponibilidad() == null
        || aEsquemaTurno.getStaffMedico().getDisponibilidad().isEmpty()) {
      throw new HorarioIncompatibleException(
          "No se puede asignar a "
              + aEsquemaTurno.getStaffMedico().getMedico().getNombre()
              + " "
              + aEsquemaTurno.getStaffMedico().getMedico().getApellido()
              + " los "
              + aEsquemaTurno.getDiaSemana()
              + " de "
              + aEsquemaTurno.getHoraInicio().format(formatter)
              + " a "
              + aEsquemaTurno.getHoraFin().format(formatter)
              + " al "
              + aEsquemaTurno.getConsultorio().getNombre()
              + " porque no tiene disponibilidad para el centro");
    }
    if (aEsquemaTurno.getSobreturno() < 0) {
      throw new HorarioIncompatibleException(
          "No se puede asignar a "
              + aEsquemaTurno.getStaffMedico().getMedico().getNombre()
              + " "
              + aEsquemaTurno.getStaffMedico().getMedico().getApellido()
              + " los "
              + aEsquemaTurno.getDiaSemana()
              + " de "
              + aEsquemaTurno.getHoraInicio().format(formatter)
              + " a "
              + aEsquemaTurno.getHoraFin().format(formatter)
              + " al "
              + aEsquemaTurno.getConsultorio().getNombre()
              + " porque el sobreturno es inválido");
    }
    if (aEsquemaTurno.getIntervalo() == null || aEsquemaTurno.getIntervalo() <= 0)
      throw new HorarioIncompatibleException(
          "No se puede asignar a "
              + aEsquemaTurno.getStaffMedico().getMedico().getNombre()
              + " "
              + aEsquemaTurno.getStaffMedico().getMedico().getApellido()
              + " los "
              + aEsquemaTurno.getDiaSemana()
              + " de "
              + aEsquemaTurno.getHoraInicio().format(formatter)
              + " a "
              + aEsquemaTurno.getHoraFin().format(formatter)
              + " al "
              + aEsquemaTurno.getConsultorio().getNombre()
              + " porque el intervalo es inválido");
    if (aEsquemaTurno.getHoraFin().isBefore(aEsquemaTurno.getHoraInicio())
        || Duration.between(aEsquemaTurno.getHoraInicio(), aEsquemaTurno.getHoraFin()).toMinutes()
            == 0)
      throw new HorarioIncompatibleException(
          "No se puede asignar a "
              + aEsquemaTurno.getStaffMedico().getMedico().getNombre()
              + " "
              + aEsquemaTurno.getStaffMedico().getMedico().getApellido()
              + " los "
              + aEsquemaTurno.getDiaSemana()
              + " de "
              + aEsquemaTurno.getHoraInicio().format(formatter)
              + " a "
              + aEsquemaTurno.getHoraFin().format(formatter)
              + " al "
              + aEsquemaTurno.getConsultorio().getNombre()
              + " porque el horario es inválido");
    if (Duration.between(aEsquemaTurno.getHoraInicio(), aEsquemaTurno.getHoraFin()).toMinutes()
        < aEsquemaTurno.getIntervalo())
      throw new HorarioIncompatibleException(
          "No se puede asignar a "
              + aEsquemaTurno.getStaffMedico().getMedico().getNombre()
              + " "
              + aEsquemaTurno.getStaffMedico().getMedico().getApellido()
              + " los "
              + aEsquemaTurno.getDiaSemana()
              + " de "
              + aEsquemaTurno.getHoraInicio().format(formatter)
              + " a "
              + aEsquemaTurno.getHoraFin().format(formatter)
              + " al "
              + aEsquemaTurno.getConsultorio().getNombre()
              + " porque el horario es demasiado corto respecto al intervalo");
    if (Duration.between(aEsquemaTurno.getHoraInicio(), aEsquemaTurno.getHoraFin()).toMinutes()
            % aEsquemaTurno.getIntervalo()
        != 0)
      throw new HorarioIncompatibleException(
          "No se puede asignar a "
              + aEsquemaTurno.getStaffMedico().getMedico().getNombre()
              + " "
              + aEsquemaTurno.getStaffMedico().getMedico().getApellido()
              + " los "
              + aEsquemaTurno.getDiaSemana()
              + " de "
              + aEsquemaTurno.getHoraInicio().format(formatter)
              + " a "
              + aEsquemaTurno.getHoraFin().format(formatter)
              + " al "
              + aEsquemaTurno.getConsultorio().getNombre()
              + " porque el horario no está acorde al intervalo");
    boolean conflicto = true;
    Horario horarioEsquema = new Horario(aEsquemaTurno.getHoraInicio(), aEsquemaTurno.getHoraFin());
    for (DisponibilidadMedico disponibilidad : aEsquemaTurno.getStaffMedico().getDisponibilidad()) {
      if (!aEsquemaTurno.getDiaSemana().equals(disponibilidad.getDiaSemana())) continue;
      Horario horarioMedico =
          new Horario(disponibilidad.getHoraInicio(), disponibilidad.getHoraFin());
      if (horarioMedico.contiene(horarioEsquema)) {
        conflicto = false;
        break;
      }
    }
    if (conflicto) {
      throw new HorarioIncompatibleException(
          "No se puede asignar a "
              + aEsquemaTurno.getStaffMedico().getMedico().getNombre()
              + " "
              + aEsquemaTurno.getStaffMedico().getMedico().getApellido()
              + " los "
              + aEsquemaTurno.getDiaSemana()
              + " de "
              + aEsquemaTurno.getHoraInicio().format(formatter)
              + " a "
              + aEsquemaTurno.getHoraFin().format(formatter)
              + " al "
              + aEsquemaTurno.getConsultorio().getNombre()
              + " porque el horario no es compatible con su disponibilidad para el centro");
    }
  }

  @Override
  public void checkUpdate(EsquemaTurno aEsquemaTurno) {
    EsquemaTurno oldEsquemaTurno = service.findById(aEsquemaTurno.getId());
    boolean cambios =
        oldEsquemaTurno.getDiaSemana() != aEsquemaTurno.getDiaSemana()
            || oldEsquemaTurno.getConsultorio().getId() != aEsquemaTurno.getConsultorio().getId()
            || !oldEsquemaTurno.getHoraInicio().equals(aEsquemaTurno.getHoraInicio())
            || !oldEsquemaTurno.getHoraFin().equals(aEsquemaTurno.getHoraFin())
            || !oldEsquemaTurno.getIntervalo().equals(aEsquemaTurno.getIntervalo())
            || oldEsquemaTurno.getSobreturno() != aEsquemaTurno.getSobreturno()
            || oldEsquemaTurno.getStaffMedico().getId() != aEsquemaTurno.getStaffMedico().getId();
    if (cambios) {
      List<Turno> turnos =
          turnoService.find(
              null,
              aEsquemaTurno.getConsultorio().getId(),
              aEsquemaTurno.getStaffMedico().getMedico().getId(),
              null,
              List.of(EstadoTurno.PROGRAMADO, EstadoTurno.REAGENDADO, EstadoTurno.CONFIRMADO));
      if (turnos.size() > 0)
        throw new AtributoInvalidoException(
            "El esquema de turnos tiene turnos, no se puede modificar");
    }
  }

  @Override
  public void checkCreate(EsquemaTurno object) {}

  @Override
  public void checkExists(EsquemaTurno object) {}
}
