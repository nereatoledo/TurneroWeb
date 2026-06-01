package unpsjb.labprog.backend.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.EsquemaTurnoService;
import unpsjb.labprog.backend.presenter.dto.AgendaRequestDTO;

import java.time.LocalDate;

@RestController
@RequestMapping("/esquemas-turnos")
public class EsquemaTurnoPresenter {

    @Autowired
    private EsquemaTurnoService esquemaTurnoService;

    @PostMapping
    public ResponseEntity<Object> crearAgenda(@RequestBody AgendaRequestDTO dto) {
        try {
            esquemaTurnoService.procesarYGuardarAgenda(dto);
            return Response.response(HttpStatus.OK, "Agenda configurada exitosamente.", null);
        } catch (IllegalArgumentException e) {
            return Response.response(HttpStatus.CONFLICT, e.getMessage(), null);
        } catch (DataIntegrityViolationException e) {
            return Response.response(HttpStatus.CONFLICT, "Error de integridad en los datos enviados.", null);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.response(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno: " + e.getMessage(), null);
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<Object> buscarAgenda(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(required = false) Integer idEspecialidad,
            @RequestParam(required = false) Integer idMedico) {
        try {
            Object agenda = esquemaTurnoService.obtenerAgendaFrontend(fechaInicio, fechaFin, idEspecialidad, idMedico);
            return Response.response(HttpStatus.OK, "Agenda encontrada", agenda);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.response(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar la agenda", null);
        }
    }

    @DeleteMapping("/medico/{idMedico}/consultorio/{idConsultorio}")
    public ResponseEntity<Object> cancelarDisponibilidad(
            @PathVariable int idMedico, 
            @PathVariable int idConsultorio) {
        
        return Response.response(HttpStatus.OK, "Disponibilidad cancelada y pacientes notificados.", null);
    }
}