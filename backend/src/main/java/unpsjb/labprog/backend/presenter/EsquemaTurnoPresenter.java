package unpsjb.labprog.backend.presenter;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.AgendaService;
import unpsjb.labprog.backend.business.EsquemaTurnoService;
import unpsjb.labprog.backend.presenter.dto.AgendaRequestDTO;
import unpsjb.labprog.backend.presenter.dto.AutoAgendaRequestDTO;

@RestController
@CrossOrigin
@RequestMapping("/esquemas-turnos")
public class EsquemaTurnoPresenter {
  @Autowired private EsquemaTurnoService esquemaTurnoService;
  @Autowired private AgendaService agendaService;

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Object> crearAgenda(@RequestBody AgendaRequestDTO dto) {
    esquemaTurnoService.procesarYGuardarAgenda(dto);
    return Response.ok(null, "Agenda configurada exitosamente.");
  }

  @RequestMapping(value = "/buscar", method = RequestMethod.GET)
  public ResponseEntity<Object> buscarAgenda(
      @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fechaInicio,
      @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy")
          LocalDate fechaFin,
      @RequestParam(required = false) Integer idEspecialidad,
      @RequestParam(required = false) Integer idMedico,
      @RequestParam(required = false) Integer idCentro) {
    return Response.ok(
        agendaService.generate(idEspecialidad, idMedico, idCentro, fechaInicio, fechaFin),
        "Agenda encontrada");
  }

  @RequestMapping(
      value = "/medico/{idMedico}/consultorio/{idConsultorio}",
      method = RequestMethod.DELETE)
  public ResponseEntity<Object> cancelarDisponibilidad(
      @PathVariable int idMedico, @PathVariable int idConsultorio) {
    esquemaTurnoService.cancelarDisponibilidad(idMedico, idConsultorio);
    return Response.ok(null, "Disponibilidad cancelada y pacientes notificados.");
  }

  @RequestMapping(value = "/auto-asignar", method = RequestMethod.POST)
  public ResponseEntity<Object> autoAsignarAgenda(@RequestBody AutoAgendaRequestDTO dto) {
    return Response.ok(agendaService.autoAsignarAgenda(dto), "Agenda auto-asignada exitosamente.");
  }
}
