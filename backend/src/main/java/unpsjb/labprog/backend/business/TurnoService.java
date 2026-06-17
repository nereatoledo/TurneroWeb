package unpsjb.labprog.backend.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import unpsjb.labprog.backend.model.EstadoTurno;
import unpsjb.labprog.backend.model.Paciente;
import unpsjb.labprog.backend.model.Turno;
import unpsjb.labprog.backend.presenter.dto.TurnoConfirmacionResultado;

@Service
public class TurnoService {

    private static final int MINUTOS_RESERVA = 15;

    private static final List<EstadoTurno> ESTADOS_ACTIVOS = Arrays.asList(
            EstadoTurno.PROGRAMADO, EstadoTurno.RESERVADO, EstadoTurno.CONFIRMADO);

    private static final List<EstadoTurno> ESTADOS_DISPONIBLES = Arrays.asList(
            EstadoTurno.PROGRAMADO, EstadoTurno.CANCELADO);

    @Autowired
    TurnoRepository repository;

    public List<Turno> findAll() {
        List<Turno> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        return result;
    }

    public Turno findById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Page<Turno> buscarPorPaciente(int pacienteId, EstadoTurno estado, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "fecha", "horaInicio"));
        return repository.search(null, estado, pacienteId, null, null, pageable);
    }

    @Transactional
    public Turno save(Turno e) {
        return repository.save(e);
    }

    @Transactional
    public void delete(int id) {
        Turno t = repository.findById(id).orElse(null);
        if (t != null) {
            LocalDateTime fechaHora = LocalDateTime.of(t.getFecha(), t.getHoraInicio());
            if (fechaHora.isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("No se puede cancelar un turno cuya fecha y hora ya han pasado.");
            }
            t.setEstado(EstadoTurno.CANCELADO);
            repository.save(t);
        }
    }

    @Transactional
    public Turno reservar(int turnoId, Paciente paciente) {
        Turno turno = repository.findById(turnoId)
                .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado."));

        LocalDateTime fechaHoraTurno = LocalDateTime.of(turno.getFecha(), turno.getHoraInicio());
        if (fechaHoraTurno.isBefore(LocalDateTime.now(java.time.ZoneId.of("America/Argentina/Buenos_Aires")))) {
            throw new IllegalArgumentException("El turno seleccionado ya pasó.");
        }

        if (turno.getMedico() != null && paciente.getId() > 0) {
            boolean yaExiste = repository.existeTurnoMismoMedicoMisDia(
                    paciente.getId(),
                    turno.getMedico().getId(),
                    turno.getFecha(),
                    ESTADOS_ACTIVOS
            );
            if (yaExiste) {
                throw new IllegalArgumentException(
                        "Ya tenés un turno con este médico para ese día. No podés reservar otro.");
            }
        }

        int filas = repository.reservar(
                turnoId, paciente, EstadoTurno.RESERVADO,
                ESTADOS_DISPONIBLES, LocalDateTime.now()
        );
        if (filas == 0) {
            throw new IllegalStateException(
                    "Ese turno ya fue reservado por otro paciente. Por favor seleccioná otro.");
        }
        return repository.findById(turnoId).orElseThrow();
    }

    public List<Turno> buscarParaReprogramar(int turnoOrigenId) {
        Turno origen = repository.findById(turnoOrigenId)
                .orElseThrow(() -> new IllegalArgumentException("Turno origen no encontrado."));

        LocalDate desde = LocalDate.now().plusDays(1);
        LocalDate hasta = desde.plusDays(7);

        List<Turno> disponibles = repository.buscarParaReprogramar(
                origen.getMedico().getId(), desde, hasta,
                Arrays.asList(EstadoTurno.PROGRAMADO, EstadoTurno.CANCELADO)
        );

        if (disponibles.isEmpty()) {
            throw new NoSuchElementException(
                    "No hay turnos disponibles con este médico en los próximos 7 días.");
        }
        return disponibles;
    }

    @Transactional
    public void liberarReservasVencidas() {
        LocalDateTime limite = LocalDateTime.now().minusMinutes(MINUTOS_RESERVA);
        int liberadas = repository.liberarReservasVencidas(limite);
        if (liberadas > 0) {
            System.out.println("[Scheduler] Reservas vencidas liberadas: " + liberadas);
        }
    }

    @Transactional
    public void cancelarReserva(int turnoId, int pacienteId) {
        int filasAfectadas = repository.cancelarReserva(turnoId, pacienteId);
        if (filasAfectadas == 0) {
            throw new IllegalArgumentException("No se pudo deshacer la reserva. Verifique que el turno esté en estado RESERVADO y le pertenezca.");
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
        Turno turno = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado."));

        if (!forzar && turno.getMedico() != null && aPaciente.getId() > 0
                && turno.getMedico().getEspecialidad() != null) {
            List<Turno> conflictos = repository.buscarTurnosMismaEspecialidadMisDia(
                    aPaciente.getId(),
                    turno.getMedico().getEspecialidad().getId(),
                    turno.getFecha(),
                    ESTADOS_ACTIVOS
            );
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

        int filasAfectadas = repository.confirmar(
                id, aPaciente, EstadoTurno.CONFIRMADO,
                Arrays.asList(EstadoTurno.PROGRAMADO, EstadoTurno.CANCELADO, EstadoTurno.RESERVADO)
        );
        if (filasAfectadas == 0) {
            throw new IllegalArgumentException(
                    "El turno no se encuentra disponible. Por favor seleccione otro horario.");
        }
        return TurnoConfirmacionResultado.ok(repository.findById(id).orElse(null));
    }

    public List<Turno> buscarTurnosConFiltros(Integer especialidadId, Integer medicoId, Integer centroId) {
        return repository.buscarDisponiblesParaPaciente(
            Arrays.asList(EstadoTurno.PROGRAMADO, EstadoTurno.CANCELADO),
            especialidadId,
            medicoId,
            centroId,
            LocalDate.now(java.time.ZoneId.of("America/Argentina/Buenos_Aires")),
            LocalTime.now(java.time.ZoneId.of("America/Argentina/Buenos_Aires"))
        );
    }
}