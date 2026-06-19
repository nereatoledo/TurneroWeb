package unpsjb.labprog.backend.validations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import unpsjb.labprog.backend.business.TurnoRepository;
import unpsjb.labprog.backend.model.EstadoTurno;
import unpsjb.labprog.backend.model.Turno;
import unpsjb.labprog.backend.presenter.dto.TurnoConfirmacionResultado;

@Component
public class TurnoValidator {

    private static final int MAX_CANCELACIONES_TARDIAS = 4;

    private static final List<EstadoTurno> ESTADOS_ACTIVOS = Arrays.asList(
            EstadoTurno.PROGRAMADO, EstadoTurno.CONFIRMADO);

    @Autowired
    private TurnoRepository repository;

    public void validarPenalizaciones(Integer pacienteId) {
        if (pacienteId != null && pacienteId > 0) {
            LocalDate tresMesesAtras = LocalDate.now().minusMonths(3);

            long tardias = repository.countCancelacionesTardias(pacienteId, EstadoTurno.CANCELADO_TARDIO,
                    tresMesesAtras);

            if (tardias >= MAX_CANCELACIONES_TARDIAS) {
                throw new IllegalArgumentException(
                        "Estás restringido para operar con turnos temporalmente por acumular " +
                                MAX_CANCELACIONES_TARDIAS + " o más cancelaciones tardías en los últimos meses.");
            }
        }
    }

    public void validarCancelacion(Turno t) {
        LocalDateTime fechaHora = LocalDateTime.of(t.getFecha(), t.getHoraInicio());
        if (fechaHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("No se puede cancelar un turno cuya fecha y hora ya han pasado.");
        }
    }

    public void validarCancelacionReserva(Turno turno, int pacienteId) {
        if (turno.getPaciente() == null || turno.getPaciente().getId() != pacienteId
                || turno.getEstado() != EstadoTurno.PROGRAMADO) {
            throw new IllegalArgumentException(
                    "No se pudo deshacer la reserva. Verifique que el turno esté en estado PROGRAMADO y le pertenezca.");
        }
    }

    public void validarReserva(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin,
            Integer pacienteId, Integer medicoId, Integer consultorioId) {

        validarPenalizaciones(pacienteId);

        LocalDateTime fechaHoraTurno = LocalDateTime.of(fecha, horaInicio);
        if (fechaHoraTurno.isBefore(LocalDateTime.now(java.time.ZoneId.of("America/Argentina/Buenos_Aires")))) {
            throw new IllegalArgumentException("El turno seleccionado ya pasó.");
        }

        if (medicoId != null && pacienteId != null && pacienteId > 0) {
            boolean yaExiste = repository.existeTurnoMismoMedicoMisDia(
                    pacienteId,
                    medicoId,
                    fecha,
                    ESTADOS_ACTIVOS);
            if (yaExiste) {
                throw new IllegalArgumentException(
                        "Ya tenés un turno con este médico para ese día. No podés reservar otro.");
            }
        }

        boolean horarioOcupado = repository.existeSuperposicion(
                fecha, horaInicio, horaFin, consultorioId, medicoId, ESTADOS_ACTIVOS);

        if (horarioOcupado) {
            throw new IllegalStateException("El horario seleccionado ya no se encuentra disponible.");
        }
    }

    public TurnoConfirmacionResultado validarConfirmacion(Turno turno, int pacienteId, boolean forzar) {
        validarPenalizaciones(pacienteId);

        if (!forzar && turno.getMedico() != null && pacienteId > 0
                && turno.getMedico().getEspecialidad() != null) {
            List<Turno> conflictos = repository.buscarTurnosMismaEspecialidadMisDia(
                    pacienteId,
                    turno.getMedico().getEspecialidad().getId(),
                    turno.getFecha(),
                    ESTADOS_ACTIVOS);
            conflictos.removeIf(t -> t.getId() == turno.getId());

            if (!conflictos.isEmpty()) {
                Turno conflicto = conflictos.get(0);
                String medNombre = conflicto.getMedico() != null
                        ? "el/la Dr./Dra. " + conflicto.getMedico().getApellido()
                        : "otro médico";
                String espNombre = turno.getMedico().getEspecialidad().getNombre();
                return TurnoConfirmacionResultado.conAdvertencia(
                        "Ya tenés un turno de " + espNombre + " ese día con " + medNombre +
                                ". ¿Querés confirmar de todas formas?");
            }
        }

        if (turno.getEstado() != EstadoTurno.PROGRAMADO) {
            throw new IllegalArgumentException("El turno no se encuentra disponible o ya no está PROGRAMADO.");
        }

        return null;
    }
}
