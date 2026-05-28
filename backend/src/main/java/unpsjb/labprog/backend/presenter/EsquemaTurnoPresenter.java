package unpsjb.labprog.backend.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.EsquemaTurnoService;
import unpsjb.labprog.backend.business.ConsultorioRepository;
import unpsjb.labprog.backend.business.StaffMedicoRepository;
import unpsjb.labprog.backend.business.CentroAtencionService;
import unpsjb.labprog.backend.model.EsquemaTurno;
import unpsjb.labprog.backend.model.Consultorio;
import unpsjb.labprog.backend.model.CentroAtencion;
import unpsjb.labprog.backend.model.StaffMedico;
import unpsjb.labprog.backend.model.DiaSemana;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/esquemas-turnos")
public class EsquemaTurnoPresenter {

    @Autowired
    private EsquemaTurnoService esquemaTurnoService;

    @Autowired
    private ConsultorioRepository consultorioRepository;

    @Autowired
    private StaffMedicoRepository staffMedicoRepository;

    @Autowired
    private CentroAtencionService centroAtencionService;

    @PostMapping
    public ResponseEntity<Object> crearAgenda(@RequestBody AgendaDTO dto) {
        try {
            Consultorio consultorio = consultorioRepository.findById(dto.getIdConsultorio()).orElse(null);
            if (consultorio == null) {
                return Response.response(HttpStatus.BAD_REQUEST, "Consultorio no encontrado.", null);
            }

            CentroAtencion centro = centroAtencionService.findCentroByConsultorioId(consultorio.getId());
            if (centro == null) {
                return Response.response(HttpStatus.BAD_REQUEST, "El consultorio no pertenece a ningún centro de atención.", null);
            }

            StaffMedico staffMedico = staffMedicoRepository.findByCentroNombreYMedicoId(centro.getNombre(), dto.getIdMedico());
            if (staffMedico == null) {
                return Response.response(HttpStatus.BAD_REQUEST, "El médico no está asociado al centro de atención de este consultorio.", null);
            }

            List<EsquemaTurno> esquemasCreados = new ArrayList<>();
            Set<DiaSemana> diasProcesados = new HashSet<>();
            
            LocalDate diaActual = dto.getFechaInicio();

            while (!diaActual.isAfter(dto.getFechaFin())) {
                DiaSemana diaJava = mapearDia(diaActual.getDayOfWeek());
                
                if (diaJava != null && !diasProcesados.contains(diaJava)) {
                    EsquemaTurno esquema = new EsquemaTurno();
                    esquema.setNombre(dto.getNombre() != null ? dto.getNombre() : "Agenda Semanal");
                    esquema.setDescripcion(dto.getDescripcion());
                    esquema.setDiaSemana(diaJava); 
                    esquema.setHoraInicio(dto.getHoraInicio());
                    esquema.setHoraFin(dto.getHoraFin());
                    esquema.setConsultorio(consultorio);
                    esquema.setStaffMedico(staffMedico);
                    
                    esquemaTurnoService.guardar(esquema);
                    esquemasCreados.add(esquema);
                    diasProcesados.add(diaJava);
                }
                
                diaActual = diaActual.plusDays(1);
            }

            return Response.response(HttpStatus.OK, "Agenda configurada exitosamente.", null);
            
        } catch (Exception e) {
            e.printStackTrace();
            return Response.response(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno: " + e.getMessage(), null);
        }
    }

    private DiaSemana mapearDia(DayOfWeek dayOfWeek) {
        switch(dayOfWeek) {
            case MONDAY: return DiaSemana.LUNES;
            case TUESDAY: return DiaSemana.MARTES;
            case WEDNESDAY: return DiaSemana.MIERCOLES;
            case THURSDAY: return DiaSemana.JUEVES;
            case FRIDAY: return DiaSemana.VIERNES;
            case SATURDAY: return DiaSemana.SABADO;
            case SUNDAY: return DiaSemana.DOMINGO;
            default: return null;
        }
    }
}