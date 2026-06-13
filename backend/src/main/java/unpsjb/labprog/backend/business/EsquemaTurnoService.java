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
import unpsjb.labprog.backend.presenter.dto.AgendaRequestDTO;
import unpsjb.labprog.backend.presenter.dto.AgendaResponseDTO;
import unpsjb.labprog.backend.presenter.dto.AgendaResponseDTO.*;

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
                        throw new IllegalArgumentException("El médico ya está asignado en otro consultorio");
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

    public List<AgendaResponseDTO> obtenerAgendaFrontend(
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

                    List<Turno> turnosOcupados = turnoRepository.find(fechaActual, esquema.getConsultorio().getId(), Arrays.asList(EstadoTurno.PROGRAMADO, EstadoTurno.CONFIRMADO, EstadoTurno.REAGENDADO));
                    List<SlotTurnoAgenda> slots = generarSlots(esquema.getHoraInicio(), esquema.getHoraFin(), intervaloMinutos, turnosOcupados);

                    EsquemaTurnoAgenda tarjeta = new EsquemaTurnoAgenda(
                            esquema.getHoraInicio(),
                            esquema.getHoraFin(),
                            esquema.getStaffMedico().getMedico(), 
                            centroInfo,
                            esquema.getConsultorio(),             
                            slots
                    );
                    
                    detallesDelDia.add(tarjeta);
                }

                AgendaResponseDTO agendaDia = new AgendaResponseDTO(fechaActual, diaJava, detallesDelDia, false);
                agendasDiarias.add(agendaDia);
            }
            fechaActual = fechaActual.plusDays(1);
        }
        return agendasDiarias;
    }

    private List<SlotTurnoAgenda> generarSlots(LocalTime inicio, LocalTime fin, int intervaloMinutos, List<Turno> turnosOcupados) {
        List<SlotTurnoAgenda> slots = new ArrayList<>();
        LocalTime actual = inicio;

        while (actual.isBefore(fin)) {
            boolean disponible = true;
            for (Turno t : turnosOcupados) {
                if (t.getHoraInicio() != null && t.getHoraInicio().equals(actual)) {
                    disponible = false;
                    break;
                }
            }
            slots.add(new SlotTurnoAgenda(actual, disponible));
            actual = actual.plusMinutes(intervaloMinutos);
        }
        return slots;
    }
}
