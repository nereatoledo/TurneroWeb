package unpsjb.labprog.backend.business;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import unpsjb.labprog.backend.model.EstadoTurno;
import unpsjb.labprog.backend.model.ModificacionTurno;
import unpsjb.labprog.backend.model.Paciente;
import unpsjb.labprog.backend.model.Turno;
import unpsjb.labprog.backend.presenter.dto.TurnoConfirmacionResultado;
import unpsjb.labprog.backend.validations.TurnoValidator;

@Service
public class TurnoService {
  private static final int MINUTOS_RESERVA = 15;
  private static final int HORAS_LIMITE_CANCELACION = 24;
  @Autowired TurnoRepository repository;
  @Autowired PacienteRepository pacienteRepository;
  @Autowired MedicoRepository medicoRepository;
  @Autowired ConsultorioRepository consultorioRepository;
  @Autowired ModificacionTurnoRepository modificacionTurnoRepository;
  @Autowired TurnoValidator turnoValidator;

  public List<Turno> findAll() {
    List<Turno> result = new ArrayList<>();
    repository.findAll().forEach(result::add);
    return result;
  }

  public Turno findById(int id) {
    return repository.findById(id).orElse(null);
  }

  public List<Turno> find(
      LocalDate fecha,
      Integer consultorioId,
      Integer medicoId,
      Integer pacienteId,
      List<EstadoTurno> estados) {
    return repository.find(fecha, consultorioId, medicoId, pacienteId, estados);
  }

  public Page<Turno> buscarPorPaciente(int pacienteId, EstadoTurno estado, int page, int size) {
    Pageable pageable =
        PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "fecha", "horaInicio"));
    return repository.search(null, estado, pacienteId, null, null, pageable);
  }

  @Transactional
  public Turno save(Turno e) {
    return repository.save(e);
  }

  @Transactional
  public void delete(int id) {
    Turno t =
        repository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado"));
    turnoValidator.validarCancelacion(t);
    LocalDateTime fechaHora = LocalDateTime.of(t.getFecha(), t.getHoraInicio());
    LocalDateTime limite24hs = fechaHora.minusHours(HORAS_LIMITE_CANCELACION);
    EstadoTurno estadoAnterior = t.getEstado();
    if (LocalDateTime.now(java.time.ZoneId.of("America/Argentina/Buenos_Aires"))
        .isAfter(limite24hs)) {
      registrarCambioEstado(
          t,
          estadoAnterior,
          EstadoTurno.CANCELADO_TARDIO,
          "Cancelación tardía ejecutada por el paciente");
      if (t.getPaciente() != null) {
        long tardias =
            repository.countCancelacionesTardias(
                t.getPaciente().getId(),
                EstadoTurno.CANCELADO_TARDIO,
                LocalDate.now().minusMonths(3));
        if (tardias >= 3) {
          Paciente p = t.getPaciente();
          p.setFechaFinPenalizacion(LocalDate.now().plusDays(30));
          pacienteRepository.save(p);
          throw new IllegalArgumentException(
              "Ha superado el límite de cancelaciones permitidas en el último mes");
        }
      }
    } else {
      registrarCambioEstado(
          t, estadoAnterior, EstadoTurno.CANCELADO, "Cancelación normal ejecutada por el paciente");
    }
  }

  @Transactional
  public Turno registrarNuevoTurno(
      LocalDate fecha,
      LocalTime horaInicio,
      LocalTime horaFin,
      Integer pacienteId,
      Integer medicoId,
      Integer consultorioId) {
    turnoValidator.validarReserva(fecha, horaInicio, horaFin, pacienteId, medicoId, consultorioId);
    Turno nuevoTurno = new Turno();
    nuevoTurno.setFecha(fecha);
    nuevoTurno.setHoraInicio(horaInicio);
    nuevoTurno.setHoraFin(horaFin);
    nuevoTurno.setEstado(EstadoTurno.PROGRAMADO);
    nuevoTurno.setTimestamp(LocalDateTime.now());
    if (pacienteId != null && pacienteId > 0) {
      nuevoTurno.setPaciente(pacienteRepository.findById(pacienteId).orElse(null));
    }
    if (medicoId != null) {
      nuevoTurno.setMedico(
          medicoRepository
              .findById(medicoId)
              .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado")));
    }
    nuevoTurno.setConsultorio(
        consultorioRepository
            .findById(consultorioId)
            .orElseThrow(() -> new IllegalArgumentException("Consultorio no encontrado")));
    return repository.save(nuevoTurno);
  }

  @Transactional
  @Scheduled(fixedRate = 60000)
  public void limpiarReservasVencidas() {
    LocalDateTime limite =
        LocalDateTime.now(java.time.ZoneId.of("America/Argentina/Buenos_Aires"))
            .minusMinutes(MINUTOS_RESERVA);
    List<Turno> reservasVencidas =
        repository.findByEstadoAndTimestampBefore(EstadoTurno.PROGRAMADO, limite);
    for (Turno turno : reservasVencidas) {
      EstadoTurno estadoAnterior = turno.getEstado();
      registrarCambioEstado(
          turno,
          estadoAnterior,
          EstadoTurno.CANCELADO,
          "Reserva auto-cancelada por el sistema (Expiró tiempo de 15 min)");
    }
    LocalDateTime limiteConfirmacion =
        LocalDateTime.now(java.time.ZoneId.of("America/Argentina/Buenos_Aires"))
            .plusHours(HORAS_LIMITE_CANCELACION);
    List<Turno> programados = repository.findByEstados(Arrays.asList(EstadoTurno.PROGRAMADO));
    int autoCancelados = 0;
    for (Turno turno : programados) {
      LocalDateTime fechaHoraTurno = LocalDateTime.of(turno.getFecha(), turno.getHoraInicio());
      if (!fechaHoraTurno.isAfter(limiteConfirmacion)) {
        registrarCambioEstado(
            turno,
            turno.getEstado(),
            EstadoTurno.CANCELADO,
            "Cancelado automáticamente por falta de confirmación previa a las 24hs");
        autoCancelados++;
      }
    }
    if (!reservasVencidas.isEmpty() || autoCancelados > 0) {
      System.out.println(
          "[Scheduler] Reservas vencidas procesadas y liberadas: "
              + reservasVencidas.size()
              + ", Auto-cancelados: "
              + autoCancelados);
    }
  }

  @Transactional
  public void cancelarPorAdministrador(int turnoId, String motivo) {
    Turno turno =
        repository
            .findById(turnoId)
            .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado."));
    EstadoTurno estadoAnterior = turno.getEstado();
    registrarCambioEstado(turno, estadoAnterior, EstadoTurno.CANCELADO, motivo);
  }

  @Transactional
  @Scheduled(fixedRate = 300000)
  public void limpiarTurnosFinalizados() {
    List<Turno> turnosConfirmados = repository.findByEstados(Arrays.asList(EstadoTurno.CONFIRMADO));
    for (Turno turno : turnosConfirmados) {
      LocalTime horaReferencia =
          turno.getHoraFin() != null ? turno.getHoraFin() : turno.getHoraInicio();
      LocalDateTime vencimiento = LocalDateTime.of(turno.getFecha(), horaReferencia);
      if (LocalDateTime.now(java.time.ZoneId.of("America/Argentina/Buenos_Aires"))
          .isAfter(vencimiento)) {
        EstadoTurno estadoAnterior = turno.getEstado();
        registrarCambioEstado(
            turno,
            estadoAnterior,
            EstadoTurno.FINALIZADO,
            "Auto-finalizado por el sistema tras cumplirse el horario");
      }
    }
  }

  @Transactional
  public void cancelarReserva(int turnoId, int pacienteId) {
    Turno turno =
        repository
            .findById(turnoId)
            .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado."));
    turnoValidator.validarCancelacionReserva(turno, pacienteId);
    LocalDateTime fechaHora = LocalDateTime.of(turno.getFecha(), turno.getHoraInicio());
    LocalDateTime limite24hs = fechaHora.minusHours(HORAS_LIMITE_CANCELACION);
    EstadoTurno estadoAnterior = turno.getEstado();
    if (LocalDateTime.now(java.time.ZoneId.of("America/Argentina/Buenos_Aires"))
        .isAfter(limite24hs)) {
      registrarCambioEstado(
          turno,
          estadoAnterior,
          EstadoTurno.CANCELADO_TARDIO,
          "Cancelación tardía ejecutada por el usuario antes de confirmar");
      if (turno.getPaciente() != null) {
        long tardias =
            repository.countCancelacionesTardias(
                turno.getPaciente().getId(),
                EstadoTurno.CANCELADO_TARDIO,
                LocalDate.now().minusMonths(3));
        if (tardias >= 3) {
          Paciente p = turno.getPaciente();
          p.setFechaFinPenalizacion(LocalDate.now().plusDays(30));
          pacienteRepository.save(p);
        }
      }
    } else {
      registrarCambioEstado(
          turno,
          estadoAnterior,
          EstadoTurno.CANCELADO,
          "Reserva cancelada por el usuario antes de confirmar");
    }
  }

  @Transactional
  public Turno confirmar(int id, Paciente aPaciente) {
    TurnoConfirmacionResultado resultado = confirmar(id, aPaciente, false);
    if (resultado.isRequiereConfirmacion()) {
      throw new IllegalArgumentException(resultado.getAdvertencia());
    }
    return resultado.getTurno();
  }

  @Transactional
  public TurnoConfirmacionResultado confirmar(int id, Paciente aPaciente, boolean forzar) {
    Turno turno =
        repository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado."));
    TurnoConfirmacionResultado advertencia =
        turnoValidator.validarConfirmacion(turno, aPaciente.getId(), forzar);
    if (advertencia != null) {
      return advertencia;
    }
    EstadoTurno estadoAnterior = turno.getEstado();
    turno.setPaciente(
        pacienteRepository
            .findById(aPaciente.getId())
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "El paciente proporcionado es inválido o no existe.")));
    registrarCambioEstado(
        turno,
        estadoAnterior,
        EstadoTurno.CONFIRMADO,
        "Turno confirmado exitosamente por el paciente");
    return TurnoConfirmacionResultado.ok(turno);
  }

  private void registrarCambioEstado(
      Turno turno, EstadoTurno estadoAnterior, EstadoTurno nuevoEstado, String motivo) {
    turnoValidator.validarCambioEstado(estadoAnterior, nuevoEstado);
    turno.setEstado(nuevoEstado);
    repository.save(turno);
    ModificacionTurno mod =
        new ModificacionTurno(
            0,
            LocalDateTime.now(java.time.ZoneId.of("America/Argentina/Buenos_Aires")),
            estadoAnterior,
            nuevoEstado,
            motivo,
            turno);
    modificacionTurnoRepository.save(mod);
  }

  @Transactional
  public Turno reprogramarTurno(
      int turnoId,
      LocalDate nuevaFecha,
      LocalTime nuevaHoraInicio,
      LocalTime nuevaHoraFin,
      int consultorioId) {
    Turno turno =
        repository
            .findById(turnoId)
            .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado."));
    Integer pacienteId = turno.getPaciente() != null ? turno.getPaciente().getId() : null;
    turnoValidator.validarReserva(
        nuevaFecha,
        nuevaHoraInicio,
        nuevaHoraFin,
        pacienteId,
        turno.getMedico().getId(),
        consultorioId);
    LocalDateTime fechaHoraTurno = LocalDateTime.of(turno.getFecha(), turno.getHoraInicio());
    if (fechaHoraTurno.isBefore(
        LocalDateTime.now(java.time.ZoneId.of("America/Argentina/Buenos_Aires")))) {
      throw new IllegalArgumentException("No es posible reprogramar un turno pasado");
    }
    if (nuevaFecha.getYear() == 2099) {
      throw new IllegalArgumentException("No se puede reprogramar más de dos veces el mismo turno");
    }
    EstadoTurno estadoAnterior = turno.getEstado();
    turno.setFecha(nuevaFecha);
    turno.setHoraInicio(nuevaHoraInicio);
    turno.setHoraFin(nuevaHoraFin);
    turno.setConsultorio(
        consultorioRepository
            .findById(consultorioId)
            .orElseThrow(() -> new IllegalArgumentException("Consultorio no encontrado")));
    registrarCambioEstado(
        turno, estadoAnterior, EstadoTurno.REAGENDADO, "Reprogramado por el paciente");
    return turno;
  }
}
