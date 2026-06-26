package unpsjb.labprog.backend.validations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unpsjb.labprog.backend.business.PacienteRepository;
import unpsjb.labprog.backend.business.TurnoRepository;
import unpsjb.labprog.backend.exceptions.AtributoInvalidoException;
import unpsjb.labprog.backend.model.EstadoTurno;
import unpsjb.labprog.backend.model.Paciente;
import unpsjb.labprog.backend.model.Turno;
import unpsjb.labprog.backend.presenter.dto.TurnoConfirmacionResultado;

@Component
public class TurnoValidator implements Validator<Turno> {
  private static final List<EstadoTurno> ESTADOS_ACTIVOS =
      Arrays.asList(EstadoTurno.PROGRAMADO, EstadoTurno.CONFIRMADO);
  @Autowired private TurnoRepository repository;
  @Autowired private PacienteRepository pacienteRepository;

  @Override
  public void checkFormat(Turno aTurno) {
    if (aTurno.getFecha() == null)
      throw new AtributoInvalidoException("La fecha del turno es obligatoria");
    if (aTurno.getHoraInicio() == null)
      throw new AtributoInvalidoException("La hora de inicio del turno es obligatoria");
    if (aTurno.getHoraFin() == null)
      throw new AtributoInvalidoException("La hora de fin del turno es obligatoria");
  }

  @Override
  public void checkCreate(Turno aTurno) {
    checkFormat(aTurno);
    if (aTurno.getEstado() != EstadoTurno.PROGRAMADO
        && aTurno.getEstado() != EstadoTurno.CONFIRMADO)
      throw new AtributoInvalidoException(
          "Un turno nuevo solo puede crearse en estado PROGRAMADO o CONFIRMADO");
  }

  @Override
  public void checkUpdate(Turno newTurno) {
    Turno oldTurno = repository.findById(newTurno.getId()).orElse(null);
    if (oldTurno == null) throw new AtributoInvalidoException("El turno no existe");
    validarCambioEstado(oldTurno.getEstado(), newTurno.getEstado());
  }

  @Override
  public void checkExists(Turno object) {}

  public void validarPenalizaciones(Integer pacienteId) {
    if (pacienteId != null && pacienteId > 0) {
      Paciente p = pacienteRepository.findById(pacienteId).orElse(null);
      if (p != null
          && p.getFechaFinPenalizacion() != null
          && p.getFechaFinPenalizacion().isAfter(LocalDate.now())) {
        throw new AtributoInvalidoException(
            "El paciente se encuentra penalizado hasta el " + p.getFechaFinPenalizacion());
      }
    }
  }

  public void validarCambioEstado(EstadoTurno actual, EstadoTurno nuevo) {
    if (actual == EstadoTurno.CONFIRMADO && nuevo == EstadoTurno.PROGRAMADO)
      throw new AtributoInvalidoException("Un turno CONFIRMADO no puede volver a PROGRAMADO.");
    if ((actual == EstadoTurno.CANCELADO || actual == EstadoTurno.CANCELADO_TARDIO)
        && actual != nuevo)
      throw new AtributoInvalidoException("No se puede reactivar un turno cancelado");
    if (actual == EstadoTurno.REAGENDADO
        && (nuevo != EstadoTurno.CONFIRMADO
            && nuevo != EstadoTurno.CANCELADO
            && nuevo != EstadoTurno.CANCELADO_TARDIO))
      throw new AtributoInvalidoException(
          "Un turno REAGENDADO solo puede pasar a CONFIRMADO o CANCELADO / CANCELADO_TARDIO.");
  }

  public void validarCancelacion(Turno t) {
    LocalDateTime fechaHora = LocalDateTime.of(t.getFecha(), t.getHoraInicio());
    if (fechaHora.isBefore(
        LocalDateTime.now(java.time.ZoneId.of("America/Argentina/Buenos_Aires")))) {
      throw new AtributoInvalidoException(
          "No se puede cancelar un turno cuya fecha y hora ya han pasado.");
    }
  }

  public void validarCancelacionReserva(Turno turno, int pacienteId) {
    if (turno.getPaciente() == null
        || turno.getPaciente().getId() != pacienteId
        || turno.getEstado() != EstadoTurno.PROGRAMADO) {
      throw new AtributoInvalidoException(
          "No se pudo deshacer la reserva. Verifique que el turno esté en estado PROGRAMADO y le"
              + " pertenezca.");
    }
  }

  public void validarReserva(
      LocalDate fecha,
      LocalTime horaInicio,
      LocalTime horaFin,
      Integer pacienteId,
      Integer medicoId,
      Integer consultorioId) {
    validarPenalizaciones(pacienteId);
    LocalDateTime fechaHoraTurno = LocalDateTime.of(fecha, horaInicio);
    if (fechaHoraTurno.isBefore(
        LocalDateTime.now(java.time.ZoneId.of("America/Argentina/Buenos_Aires")))) {
      throw new AtributoInvalidoException("El turno seleccionado ya pasó.");
    }
    if (medicoId != null && pacienteId != null && pacienteId > 0) {
      boolean yaExiste =
          repository.existeTurnoMismoMedicoMisDia(pacienteId, medicoId, fecha, ESTADOS_ACTIVOS);
      if (yaExiste)
        throw new AtributoInvalidoException(
            "Ya tenés un turno con este médico para ese día. No podés reservar otro.");
    }
    boolean horarioOcupado =
        repository.existeSuperposicion(
            fecha, horaInicio, horaFin, consultorioId, medicoId, ESTADOS_ACTIVOS);
    if (horarioOcupado)
      throw new AtributoInvalidoException("El horario seleccionado ya no se encuentra disponible.");
  }

  public TurnoConfirmacionResultado validarConfirmacion(
      Turno turno, int pacienteId, boolean forzar) {
    validarPenalizaciones(pacienteId);
    if (!forzar
        && turno.getMedico() != null
        && pacienteId > 0
        && turno.getMedico().getEspecialidad() != null) {
      List<Turno> conflictos =
          repository.buscarTurnosMismaEspecialidadMisDia(
              pacienteId,
              turno.getMedico().getEspecialidad().getId(),
              turno.getFecha(),
              ESTADOS_ACTIVOS);
      conflictos.removeIf(t -> t.getId() == turno.getId());
      if (!conflictos.isEmpty()) {
        Turno conflicto = conflictos.get(0);
        String medNombre =
            conflicto.getMedico() != null
                ? "el/la Dr./Dra. " + conflicto.getMedico().getApellido()
                : "otro médico";
        String espNombre = turno.getMedico().getEspecialidad().getNombre();
        return TurnoConfirmacionResultado.conAdvertencia(
            "Ya tenés un turno de "
                + espNombre
                + " ese día con "
                + medNombre
                + ". ¿Querés confirmar de todas formas?");
      }
    }
    if (turno.getEstado() != EstadoTurno.PROGRAMADO)
      throw new AtributoInvalidoException(
          "El turno no se encuentra disponible o ya no está PROGRAMADO.");
    return null;
  }
}
