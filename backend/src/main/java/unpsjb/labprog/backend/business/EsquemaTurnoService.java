package unpsjb.labprog.backend.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.labprog.backend.model.CentroAtencion;
import unpsjb.labprog.backend.model.Consultorio;
import unpsjb.labprog.backend.model.DiaSemana;
import unpsjb.labprog.backend.model.EstadoTurno;
import unpsjb.labprog.backend.model.EsquemaTurno;
import unpsjb.labprog.backend.model.StaffMedico;
import unpsjb.labprog.backend.model.Turno;
import unpsjb.labprog.backend.model.Feriado;
import unpsjb.labprog.backend.presenter.dto.AgendaBusquedaResultadoDTO;
import unpsjb.labprog.backend.presenter.dto.AgendaRequestDTO;
import unpsjb.labprog.backend.presenter.dto.AgendaResponseDTO;
import unpsjb.labprog.backend.presenter.dto.AgendaResponseDTO.*;
import unpsjb.labprog.backend.presenter.dto.AutoAgendaRequestDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EsquemaTurnoService {

    @Autowired
    private EsquemaTurnoRepository esquemaTurnoRepository;
    @Autowired
    private ConsultorioRepository consultorioRepository;
    @Autowired
    private CentroAtencionService centroAtencionService;
    @Autowired
    private StaffMedicoRepository staffMedicoRepository;
    @Autowired
    private FeriadoRepository feriadoRepository;
    @Autowired
    private TurnoRepository turnoRepository;

    @Transactional
    public void procesarYGuardarAgenda(AgendaRequestDTO dto) {
        
        Consultorio consultorio = consultorioRepository.findById(dto.getIdConsultorio())
                .orElseThrow(() -> new IllegalArgumentException("Consultorio no encontrado."));

        CentroAtencion centro = centroAtencionService.findCentroByConsultorioId(consultorio.getId());
        if (centro == null) {
            throw new IllegalArgumentException("El consultorio no pertenece a ningún centro de atención.");
        }

        StaffMedico staffMedico = null;
        if (dto.getIdMedico() != null) {
            staffMedico = staffMedicoRepository.findByCentroNombreYMedicoId(centro.getNombre(), dto.getIdMedico());
            if (staffMedico == null) {
                throw new IllegalArgumentException("El médico no está asociado al centro de atención de este consultorio.");
            }
        }

        Set<DiaSemana> diasProcesados = new HashSet<>();
        LocalDate diaActual = dto.getFechaInicio();

        while (!diaActual.isAfter(dto.getFechaFin()) && diasProcesados.size() < 7) {
            DiaSemana diaJava = DiaSemana.desdeJava(diaActual.getDayOfWeek());

            if (diaJava != null && !diasProcesados.contains(diaJava)) {
                
                boolean hayConflictoConsultorio = esquemaTurnoRepository.existeConflictoEnConsultorio(
                        consultorio.getId(), diaJava, dto.getHoraInicio(), dto.getHoraFin()
                );
                if (hayConflictoConsultorio) {
                    throw new IllegalArgumentException("Conflicto de horarios en el consultorio");
                }

                if (staffMedico != null) {
                    boolean hayConflictoMedico = esquemaTurnoRepository.existeConflictoParaMedico(
                            staffMedico.getId(), diaJava, dto.getHoraInicio(), dto.getHoraFin()
                    );
                    if (hayConflictoMedico) {
                        System.out.println("DEBUG: Conflicto medico!"); throw new IllegalArgumentException("El médico ya está asignado en otro consultorio");
                    }
                }

                EsquemaTurno esquema = new EsquemaTurno();
                esquema.setNombre(dto.getNombre() != null ? dto.getNombre() : "Agenda Semanal");
                esquema.setDescripcion(dto.getDescripcion());
                esquema.setDiaSemana(diaJava);
                esquema.setHoraInicio(dto.getHoraInicio());
                esquema.setHoraFin(dto.getHoraFin());
                esquema.setConsultorio(consultorio);
                esquema.setStaffMedico(staffMedico); 

                esquemaTurnoRepository.save(esquema);
                diasProcesados.add(diaJava);
            }
            diaActual = diaActual.plusDays(1);
        }
    }

    public AgendaBusquedaResultadoDTO buscarConFallback(
            LocalDate fechaInicio,
            LocalDate fechaFin,
            Integer idEspecialidad,
            Integer idMedico,
            Integer idCentro) {

        List<DiaSemana> diasSemana = obtenerDiasSemana(fechaInicio, fechaFin);

        List<AgendaResponseDTO> agendas = obtenerAgendaFrontend(
                fechaInicio, fechaFin, idEspecialidad, idMedico, idCentro, null, null);

        if (tieneBloquesLibres(agendas)) {
            return new AgendaBusquedaResultadoDTO(false, null, agendas);
        }

        if (idMedico != null) {

            if (idCentro != null &&
                    esquemaTurnoRepository.existeEsquemaParaDias(
                            diasSemana, idEspecialidad, idMedico, null, null, idCentro)) {

                List<AgendaResponseDTO> fallbackA = obtenerAgendaFrontend(
                        fechaInicio, fechaFin, idEspecialidad, idMedico, null, null, idCentro);
                if (tieneBloquesLibres(fallbackA)) {
                    return new AgendaBusquedaResultadoDTO(
                            true,
                            "El médico no tiene disponibilidad en el centro seleccionado. " +
                            "Te mostramos su agenda en otros centros de atención.",
                            fallbackA);
                }
            }

            if (esquemaTurnoRepository.existeEsquemaParaDias(
                    diasSemana, idEspecialidad, null, null, idMedico, null)) {

                List<AgendaResponseDTO> fallbackB = obtenerAgendaFrontend(
                        fechaInicio, fechaFin, idEspecialidad, null, null, idMedico, null);
                if (tieneBloquesLibres(fallbackB)) {
                    return new AgendaBusquedaResultadoDTO(
                            true,
                            "No hay disponibilidad para ese médico. " +
                            "Te mostramos otros médicos disponibles de la misma especialidad.",
                            fallbackB);
                }
            }
        }

        if (idCentro != null && idMedico == null &&
                esquemaTurnoRepository.existeEsquemaParaDias(
                        diasSemana, idEspecialidad, null, null, null, idCentro)) {

            List<AgendaResponseDTO> fallbackC = obtenerAgendaFrontend(
                    fechaInicio, fechaFin, idEspecialidad, null, null, null, idCentro);
            if (tieneBloquesLibres(fallbackC)) {
                return new AgendaBusquedaResultadoDTO(
                        true,
                        "No hay disponibilidad en el centro seleccionado para esa especialidad. " +
                        "Te mostramos turnos disponibles en otros centros de atención.",
                        fallbackC);
            }
        }

        return new AgendaBusquedaResultadoDTO(false, null, agendas);
    }

    private List<DiaSemana> obtenerDiasSemana(LocalDate fechaInicio, LocalDate fechaFin) {
        Set<DiaSemana> dias = new HashSet<>();
        LocalDate fecha = fechaInicio;
        while (!fecha.isAfter(fechaFin) && dias.size() < 7) {
            DiaSemana dia = DiaSemana.desdeJava(fecha.getDayOfWeek());
            if (dia != null) dias.add(dia);
            fecha = fecha.plusDays(1);
        }
        return new ArrayList<>(dias);
    }

    private boolean tieneBloquesLibres(List<AgendaResponseDTO> agendas) {
        if (agendas == null || agendas.isEmpty()) return false;
        return agendas.stream()
                .filter(dia -> !dia.isEsFeriado())
                .flatMap(dia -> dia.getAgendaDetalles().stream())
                .anyMatch(esquema -> !esquema.getBloquesLibres().isEmpty());
    }

    private List<AgendaResponseDTO> obtenerAgendaFrontend(
            LocalDate fechaInicio, 
            LocalDate fechaFin, 
            Integer idEspecialidad, 
            Integer idMedico,
            Integer idCentro,
            Integer idMedicoExcluido,
            Integer idCentroExcluido) {

        List<AgendaResponseDTO> agendasDiarias = new ArrayList<>();
        LocalDate fechaActual = fechaInicio;

        while (!fechaActual.isAfter(fechaFin)) {
            
            DiaSemana diaJava = DiaSemana.desdeJava(fechaActual.getDayOfWeek());

            if (feriadoRepository.existeFeriadoPorFecha(fechaActual)) {
                AgendaResponseDTO agendaDiaFeriado = new AgendaResponseDTO(fechaActual, diaJava, new ArrayList<>(), true);
                agendasDiarias.add(agendaDiaFeriado);
                fechaActual = fechaActual.plusDays(1);
                continue; 
            }

            List<EsquemaTurno> esquemasDb = esquemaTurnoRepository.buscarParaAgenda(diaJava, idEspecialidad, idMedico, idCentro, idMedicoExcluido, idCentroExcluido);

            if (!esquemasDb.isEmpty()) {
                List<EsquemaTurnoAgenda> detallesDelDia = new ArrayList<>();

                for (EsquemaTurno esquema : esquemasDb) {
                    CentroAtencion centroEntity = esquema.getStaffMedico().getCentro();
                    
                    CentroAtencionInfo centroInfo = new CentroAtencionInfo(
                            centroEntity.getNombre(),
                            centroEntity.getDireccion(),
                            centroEntity.getLocalidad(),
                            centroEntity.getProvincia(),
                            centroEntity.getTelefono(),
                            centroEntity.getCoordenadas()
                    );

                    Integer intervaloEsp = esquema.getStaffMedico().getMedico().getEspecialidad().getIntervalo();
                    
                    int intervaloMinutos = (intervaloEsp != null && intervaloEsp > 0) ? intervaloEsp : 30;

                    List<Turno> turnosOcupados = turnoRepository.find(fechaActual, esquema.getConsultorio().getId(), Arrays.asList(EstadoTurno.RESERVADO, EstadoTurno.CONFIRMADO, EstadoTurno.REAGENDADO));
                    List<BloqueLibreDTO> bloques = calcularBloquesLibres(fechaActual, esquema.getHoraInicio(), esquema.getHoraFin(), turnosOcupados);

                    if (!bloques.isEmpty()) {
                        EsquemaTurnoAgenda tarjeta = new EsquemaTurnoAgenda(
                                esquema.getHoraInicio(),
                                esquema.getHoraFin(),
                                esquema.getStaffMedico().getMedico(), 
                                centroInfo,
                                esquema.getConsultorio(),             
                                intervaloMinutos,
                                bloques
                        );
                        
                        detallesDelDia.add(tarjeta);
                    }
                }

                if (!detallesDelDia.isEmpty()) {
                    AgendaResponseDTO agendaDia = new AgendaResponseDTO(fechaActual, diaJava, detallesDelDia, false);
                    agendasDiarias.add(agendaDia);
                }
            }
            fechaActual = fechaActual.plusDays(1);
        }
        return agendasDiarias;
    }

    private List<BloqueLibreDTO> calcularBloquesLibres(LocalDate fechaActual, LocalTime inicio, LocalTime fin, List<Turno> turnosOcupados) {
        List<BloqueLibreDTO> bloquesLibres = new ArrayList<>();
        LocalDate hoy = LocalDate.now(java.time.ZoneId.of("America/Argentina/Buenos_Aires"));
        LocalTime ahora = LocalTime.now(java.time.ZoneId.of("America/Argentina/Buenos_Aires"));

        LocalTime actual = inicio;
        
        if (fechaActual.isBefore(hoy)) {
            return bloquesLibres;
        } else if (fechaActual.isEqual(hoy) && actual.isBefore(ahora)) {
            actual = ahora;
        }

        if (actual.isAfter(fin) || actual.equals(fin)) {
            return bloquesLibres;
        }

        turnosOcupados.sort((t1, t2) -> t1.getHoraInicio().compareTo(t2.getHoraInicio()));

        for (Turno t : turnosOcupados) {
            if (t.getHoraInicio().isAfter(actual) || t.getHoraInicio().equals(actual)) {
                if (t.getHoraInicio().isAfter(actual)) {
                    bloquesLibres.add(new BloqueLibreDTO(actual, t.getHoraInicio()));
                }
                if (t.getHoraFin() != null && t.getHoraFin().isAfter(actual)) {
                     actual = t.getHoraFin();
                }
            } else if (t.getHoraFin() != null && t.getHoraFin().isAfter(actual)) {
                actual = t.getHoraFin();
            }
        }

        if (actual.isBefore(fin)) {
            bloquesLibres.add(new BloqueLibreDTO(actual, fin));
        }

        return bloquesLibres;
    }


    @Transactional
    public List<EsquemaTurno> autoAsignarAgenda(AutoAgendaRequestDTO dto) {
        StaffMedico staffMedico = staffMedicoRepository.findByCentroIdYMedicoId(dto.getIdCentro(), dto.getIdMedico());
        if (staffMedico == null) {
            throw new IllegalArgumentException("El médico no está asociado al centro de atención indicado.");
        }

        List<Consultorio> consultoriosCentro = consultorioRepository.findByCentroIdOrdenados(dto.getIdCentro());
        if (consultoriosCentro.isEmpty()) {
            throw new IllegalArgumentException("El centro de atención no tiene consultorios.");
        }

        List<EsquemaTurno> esquemasGuardados = new ArrayList<>();
        Set<DiaSemana> diasProcesados = new HashSet<>();
        LocalDate diaActual = dto.getFechaInicio();

        while (!diaActual.isAfter(dto.getFechaFin()) && diasProcesados.size() < 7) {
            DiaSemana diaJava = DiaSemana.desdeJava(diaActual.getDayOfWeek());

            if (diaJava != null && !diasProcesados.contains(diaJava)) {
                
                boolean hayConflictoMedico = esquemaTurnoRepository.existeConflictoParaMedico(
                        staffMedico.getId(), diaJava, dto.getHoraInicio(), dto.getHoraFin()
                );
                if (hayConflictoMedico) {
                    throw new IllegalArgumentException("El médico ya está asignado en otro consultorio en el día " + diaJava);
                }

                LocalTime currentInicio = dto.getHoraInicio();
                List<Consultorio> consultoriosDisponibles = new ArrayList<>(consultoriosCentro);

                while (currentInicio.isBefore(dto.getHoraFin())) {
                    Consultorio mejorConsultorio = null;
                    LocalTime mejorFinLibre = currentInicio;

                    for (Consultorio c : consultoriosDisponibles) {
                        LocalTime finLibre = calcularBloqueLibre(c, diaJava, currentInicio, dto.getHoraFin());
                        if (finLibre.isAfter(mejorFinLibre)) {
                            mejorFinLibre = finLibre;
                            mejorConsultorio = c;
                        }
                        if (finLibre.equals(dto.getHoraFin())) {
                            mejorConsultorio = c;
                            mejorFinLibre = finLibre;
                            break;
                        }
                    }

                    if (mejorConsultorio == null || mejorFinLibre.equals(currentInicio)) {
                        throw new IllegalStateException("No hay consultorios disponibles para cubrir el bloque horario solicitado en el día " + diaJava);
                    }

                    EsquemaTurno esquema = new EsquemaTurno();
                    esquema.setNombre(dto.getNombre() != null ? dto.getNombre() : "Agenda Semanal Automática");
                    esquema.setDescripcion(dto.getDescripcion());
                    esquema.setDiaSemana(diaJava);
                    esquema.setHoraInicio(currentInicio);
                    esquema.setHoraFin(mejorFinLibre);
                    esquema.setConsultorio(mejorConsultorio);
                    esquema.setStaffMedico(staffMedico);

                    esquemasGuardados.add(esquemaTurnoRepository.save(esquema));

                    currentInicio = mejorFinLibre;
                    consultoriosDisponibles.remove(mejorConsultorio);
                }
                diasProcesados.add(diaJava);
            }
            diaActual = diaActual.plusDays(1);
        }
        
        return esquemasGuardados;
    }

    private LocalTime calcularBloqueLibre(Consultorio consultorio, DiaSemana dia, LocalTime inicioBuscado, LocalTime finBuscado) {
        List<EsquemaTurno> esquemas = esquemaTurnoRepository.findByConsultorioIdYDia(consultorio.getId(), dia);
        LocalTime maxLibre = finBuscado;
        
        for (EsquemaTurno eq : esquemas) {
            if (inicioBuscado.isBefore(eq.getHoraFin()) && eq.getHoraInicio().isBefore(finBuscado)) {
                if (!inicioBuscado.isBefore(eq.getHoraInicio())) {
                    return inicioBuscado;
                } else {
                    if (eq.getHoraInicio().isBefore(maxLibre)) {
                        maxLibre = eq.getHoraInicio();
                    }
                }
            }
        }
        return maxLibre;
    }
}
