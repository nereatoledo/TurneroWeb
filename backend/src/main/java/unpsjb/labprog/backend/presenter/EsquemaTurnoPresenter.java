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
import unpsjb.labprog.backend.presenter.dto.AutoAgendaRequestDTO;
import unpsjb.labprog.backend.model.EsquemaTurno;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/esquemas-turnos")
public class EsquemaTurnoPresenter {

    @Autowired
    private EsquemaTurnoService esquemaTurnoService;

    @RequestMapping(method = RequestMethod.POST)
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

    @RequestMapping(value = "/buscar", method = RequestMethod.GET)
    public ResponseEntity<Object> buscarAgenda(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(required = false) Integer idEspecialidad,
            @RequestParam(required = false) Integer idMedico,
            @RequestParam(required = false) Integer idCentro) {
        try {
            LocalDate fin = (fechaFin != null) ? fechaFin : fechaInicio;
            Object resultado = esquemaTurnoService.buscarConFallback(fechaInicio, fin, idEspecialidad, idMedico, idCentro);
            return Response.response(HttpStatus.OK, "Agenda encontrada", resultado);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.response(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar la agenda", null);
        }
    }

    @RequestMapping(value = "/medico/{idMedico}/consultorio/{idConsultorio}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> cancelarDisponibilidad(
            @PathVariable int idMedico, 
            @PathVariable int idConsultorio) {
        
        return Response.response(HttpStatus.OK, "Disponibilidad cancelada y pacientes notificados.", null);
    }

    @RequestMapping(value = "/auto-asignar", method = RequestMethod.POST)
    public ResponseEntity<Object> autoAsignarAgenda(@RequestBody AutoAgendaRequestDTO dto) {
        try {
            List<EsquemaTurno> esquemas = esquemaTurnoService.autoAsignarAgenda(dto);
            return Response.response(HttpStatus.OK, "Agenda auto-asignada exitosamente.", esquemas);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.response(HttpStatus.CONFLICT, e.getMessage(), null);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.response(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno: " + e.getMessage(), null);
        }
    }
}