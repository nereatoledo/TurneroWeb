package unpsjb.labprog.backend.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import unpsjb.labprog.backend.model.CentroAtencion;
import unpsjb.labprog.backend.model.Consultorio;
import unpsjb.labprog.backend.model.DiaSemana;
import unpsjb.labprog.backend.model.EsquemaTurno;
import unpsjb.labprog.backend.model.StaffMedico;
import unpsjb.labprog.backend.presenter.dto.AgendaRequestDTO;

import java.time.LocalDate;
import java.util.HashSet;
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
                throw new IllegalArgumentException(
                        "El médico no está asociado al centro de atención de este consultorio.");
            }
        }

        Set<DiaSemana> diasProcesados = new HashSet<>();
        LocalDate diaActual = dto.getFechaInicio();

        while (!diaActual.isAfter(dto.getFechaFin()) && diasProcesados.size() < 7) {
            DiaSemana diaJava = DiaSemana.desdeJava(diaActual.getDayOfWeek());

            if (diaJava != null && !diasProcesados.contains(diaJava)) {

                boolean hayConflictoConsultorio = esquemaTurnoRepository.existeConflictoEnConsultorio(
                        consultorio.getId(), diaJava, dto.getHoraInicio(), dto.getHoraFin());
                if (hayConflictoConsultorio) {
                    throw new IllegalArgumentException("Conflicto de horarios en el consultorio");
                }

                if (staffMedico != null) {
                    boolean hayConflictoMedico = esquemaTurnoRepository.existeConflictoParaMedico(
                            staffMedico.getId(), diaJava, dto.getHoraInicio(), dto.getHoraFin());
                    if (hayConflictoMedico) {
                        System.out.println("DEBUG: Conflicto medico!");
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

}
