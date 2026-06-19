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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import unpsjb.labprog.backend.model.EstadoTurno;
import unpsjb.labprog.backend.model.ModificacionTurno;
import unpsjb.labprog.backend.model.Paciente;
import unpsjb.labprog.backend.model.Turno;
import unpsjb.labprog.backend.presenter.dto.TurnoConfirmacionResultado;

@Service
public class TurnoService {

    private static final int MINUTOS_RESERVA = 15;

    // Ya no existe PROGRAMADO. Solo estados que ocupan lugar físico en la agenda.
    private static final List<EstadoTurno> ESTADOS_ACTIVOS = Arrays.asList(
            EstadoTurno.RESERVADO, EstadoTurno.CONFIRMADO);

    @Autowired
    TurnoRepository repository;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    ConsultorioRepository consultorioRepository;

    @Autowired
    ModificacionTurnoRepository modificacionTurnoRepository;

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
            
            LocalDateTime limite24hs = fechaHora.minusHours(24);
            EstadoTurno estadoAnterior = t.getEstado();
            
            if (LocalDateTime.now().isAfter(limite24hs)) {
                t.setEstado(EstadoTurno.CANCELADO_TARDIO);
                repository.save(t);
                
                ModificacionTurno mod = new ModificacionTurno(0, LocalDateTime.now(), estadoAnterior, EstadoTurno.CANCELADO_TARDIO, "Cancelación tardía", t);
                modificacionTurnoRepository.save(mod);
                
                if (t.getPaciente() != null) {
                    Paciente p = t.getPaciente();
                    LocalDate tresMesesAtras = LocalDate.now().minusMonths(3);
                    long tardias = repository.countCancelacionesTardias(p.getId(), EstadoTurno.CANCELADO_TARDIO, tresMesesAtras);
                    if (tardias >= 4) {
                        p.setFechaFinRestriccion(LocalDate.now().plusDays(30));
                        pacienteRepository.save(p);
                    }
                }
            } else {
                t.setEstado(EstadoTurno.CANCELADO);
                repository.save(t);

                ModificacionTurno mod = new ModificacionTurno(0, LocalDateTime.now(), estadoAnterior, EstadoTurno.CANCELADO, "Cancelación normal", t);
                modificacionTurnoRepository.save(mod);
            }
        }
    }

    @Transactional
    public Turno registrarNuevoTurno(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, 
                                     Integer pacienteId, Integer medicoId, Integer consultorioId) {
        
        if (pacienteId != null && pacienteId > 0) {
            Paciente realPaciente = pacienteRepository.findById(pacienteId)
                    .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado."));
            if (realPaciente.getFechaFinRestriccion() != null && realPaciente.getFechaFinRestriccion().isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Estás restringido para reservar turnos hasta el " + realPaciente.getFechaFinRestriccion() + " por acumular cancelaciones tardías.");
            }
        }

        LocalDateTime fechaHoraTurno = LocalDateTime.of(fecha, horaInicio);
        if (fechaHoraTurno.isBefore(LocalDateTime.now(java.time.ZoneId.of("America/Argentina/Buenos_Aires")))) {
            throw new IllegalArgumentException("El turno seleccionado ya pasó.");
        }

        if (medicoId != null && pacienteId != null && pacienteId > 0) {
            boolean yaExiste = repository.existeTurnoMismoMedicoMisDia(
                    pacienteId,
                    medicoId,
                    fecha,
                    ESTADOS_ACTIVOS
            );
            if (yaExiste) {
                throw new IllegalArgumentException(
                        "Ya tenés un turno con este médico para ese día. No podés reservar otro.");
            }
        }

        boolean horarioOcupado = repository.existeSuperposicion(
            fecha, horaInicio, horaFin, consultorioId, medicoId, ESTADOS_ACTIVOS
        );

        if (horarioOcupado) {
            throw new IllegalStateException("El horario seleccionado ya no se encuentra disponible.");
        }

        Turno nuevoTurno = new Turno();
        nuevoTurno.setFecha(fecha);
        nuevoTurno.setHoraInicio(horaInicio);
        nuevoTurno.setHoraFin(horaFin);
        nuevoTurno.setEstado(EstadoTurno.RESERVADO);
        nuevoTurno.setTimestamp(LocalDateTime.now());
        
        if (pacienteId != null && pacienteId > 0) {
            nuevoTurno.setPaciente(pacienteRepository.findById(pacienteId).orElse(null));
        }
        if (medicoId != null) {
            nuevoTurno.setMedico(medicoRepository.findById(medicoId).orElseThrow(() -> new IllegalArgumentException("Médico no encontrado")));
        }
        nuevoTurno.setConsultorio(consultorioRepository.findById(consultorioId).orElseThrow(() -> new IllegalArgumentException("Consultorio no encontrado")));

        return repository.save(nuevoTurno);
    }

    /**
     * TAREA AUTOMÁTICA: Libera turnos en estado RESERVADO que pasaron los 15 minutos
     * Se ejecuta cada 60 segundos.
     */
    @Transactional
    @Scheduled(fixedRate = 60000)
    public void limpiarReservasVencidas() {
        LocalDateTime limite = LocalDateTime.now().minusMinutes(MINUTOS_RESERVA);
        
        // Busca todas las reservas que superaron el tiempo de gracia
        List<Turno> reservasVencidas = repository.findByEstadoAndTimestampBefore(EstadoTurno.RESERVADO, limite);
        
        for (Turno turno : reservasVencidas) {
            EstadoTurno estadoAnterior = turno.getEstado();
            
            turno.setEstado(EstadoTurno.CANCELADO);
            repository.save(turno);
            
            ModificacionTurno mod = new ModificacionTurno(
                0, LocalDateTime.now(), estadoAnterior, EstadoTurno.CANCELADO, 
                "Reserva auto-cancelada por el sistema (Expiró tiempo de 15 min)", turno
            );
            modificacionTurnoRepository.save(mod);
        }
        
        if (!reservasVencidas.isEmpty()) {
            System.out.println("[Scheduler] Reservas vencidas procesadas y liberadas: " + reservasVencidas.size());
        }
    }

    @Transactional
    public void cancelarReserva(int turnoId, int pacienteId) {
        Turno turno = repository.findById(turnoId)
                .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado."));

        if (turno.getPaciente() == null || turno.getPaciente().getId() != pacienteId || turno.getEstado() != EstadoTurno.RESERVADO) {
            throw new IllegalArgumentException("No se pudo deshacer la reserva. Verifique que el turno esté en estado RESERVADO y le pertenezca.");
        }

        EstadoTurno estadoAnterior = turno.getEstado();
        turno.setEstado(EstadoTurno.CANCELADO);
        repository.save(turno);

        ModificacionTurno mod = new ModificacionTurno(
            0, LocalDateTime.now(), estadoAnterior, EstadoTurno.CANCELADO, 
            "Reserva cancelada por el usuario antes de confirmar", turno
        );
        modificacionTurnoRepository.save(mod);
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
        if (aPaciente.getId() > 0) {
            Paciente realPaciente = pacienteRepository.findById(aPaciente.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado."));
            if (realPaciente.getFechaFinRestriccion() != null && realPaciente.getFechaFinRestriccion().isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Estás restringido para confirmar turnos hasta el " + realPaciente.getFechaFinRestriccion() + " por acumular cancelaciones tardías.");
            }
        }

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

        if (turno.getEstado() != EstadoTurno.RESERVADO) {
            throw new IllegalArgumentException("El turno no se encuentra disponible o ya no está reservado.");
        }

        EstadoTurno estadoAnterior = turno.getEstado();
        
        // Confirmar el turno
        turno.setEstado(EstadoTurno.CONFIRMADO);
        turno.setPaciente(pacienteRepository.findById(aPaciente.getId()).orElseThrow());
        repository.save(turno);

        // Guardar la auditoría
        ModificacionTurno mod = new ModificacionTurno(
            0, LocalDateTime.now(), estadoAnterior, EstadoTurno.CONFIRMADO, 
            "Turno confirmado exitosamente por el paciente", turno
        );
        modificacionTurnoRepository.save(mod);

        return TurnoConfirmacionResultado.ok(turno);
    }
}