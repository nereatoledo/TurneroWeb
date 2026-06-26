package unpsjb.labprog.backend.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.AgendaService;
import unpsjb.labprog.backend.business.ModificacionTurnoService;
import unpsjb.labprog.backend.business.TurnoService;
import unpsjb.labprog.backend.model.EstadoTurno;
import unpsjb.labprog.backend.model.Paciente;
import unpsjb.labprog.backend.model.Turno;
import unpsjb.labprog.backend.presenter.dto.TurnoConfirmacionResultado;
import unpsjb.labprog.backend.presenter.dto.TurnoReservaDTO;

@RestController
@RequestMapping("turnos")
public class TurnoPresenter {
  @Autowired TurnoService service;
  @Autowired AgendaService agendaService;
  @Autowired ModificacionTurnoService modificacionTurnoService;

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<Object> findAll() {
    return Response.ok(service.findAll(), "Turnos recuperados correctamente");
  }

  @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
  public ResponseEntity<Object> findById(@PathVariable("id") Integer id) {
    Turno aTurno = service.findById(id);
    return aTurno == null
        ? Response.notFound("No existe el turno")
        : Response.ok(aTurno, "Turno recuperado");
  }

  @RequestMapping(value = "/paciente/{id}", method = RequestMethod.GET)
  public ResponseEntity<Object> findByPaciente(
      @PathVariable("id") int id,
      @RequestParam(required = false) EstadoTurno estado,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Page<Turno> turnos = service.buscarPorPaciente(id, estado, page, size);
    return Response.ok(turnos, "Turnos del paciente recuperados");
  }

  @RequestMapping(value = "/disponibles", method = RequestMethod.GET)
  public ResponseEntity<Object> buscarDisponibles(
      @RequestParam(required = false, name = "especialidad_id") Integer especialidadId,
      @RequestParam(required = false, name = "medico_id") Integer medicoId,
      @RequestParam(required = false, name = "centro_id") Integer centroId) {
    return Response.ok(
        agendaService.generate(especialidadId, medicoId, centroId, null, null),
        "Agenda recuperada con éxito");
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Object> create(@RequestBody Turno aTurno) {
    Turno savedTurno = service.save(aTurno);
    return Response.ok(savedTurno, "Turno Ingresado Correctamente");
  }

  @RequestMapping(method = RequestMethod.PUT)
  public ResponseEntity<Object> update(@RequestBody Turno aTurno) {
    service.save(aTurno);
    return Response.ok(null, "Turno Modificado Correctamente");
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Object> delete(@PathVariable("id") int id) {
    service.delete(id);
    return Response.ok(null, "Turno cancelado exitosamente");
  }

  @RequestMapping(value = "/{id}/reprogramar", method = RequestMethod.GET)
  public ResponseEntity<Object> buscarParaReprogramar(@PathVariable("id") int id) {
    Turno origen = service.findById(id);
    if (origen == null) {
      return Response.notFound("Turno origen no encontrado.");
    }
    return Response.ok(
        agendaService.generate(null, origen.getMedico().getId(), null, null, null),
        "Turnos disponibles para reprogramar");
  }

  @RequestMapping(value = "/id/{id}/historial", method = RequestMethod.GET)
  public ResponseEntity<Object> obtenerHistorial(@PathVariable("id") int id) {
    return Response.ok(
        modificacionTurnoService.obtenerHistorialPorTurno(id), "Historial recuperado con éxito");
  }

  @RequestMapping(value = "/{id}/reprogramar", method = RequestMethod.PATCH)
  public ResponseEntity<Object> confirmarReprogramacion(
      @PathVariable("id") int id, @RequestBody TurnoReservaDTO dto) {
    Turno turnoActualizado =
        service.reprogramarTurno(
            id, dto.getFecha(), dto.getHoraInicio(), dto.getHoraFin(), dto.getConsultorioId());
    return Response.ok(turnoActualizado, "Turno reprogramado exitosamente");
  }

  @RequestMapping(value = "/reservar", method = RequestMethod.POST)
  public ResponseEntity<Object> reservar(@RequestBody TurnoReservaDTO dto) {
    Turno turno =
        service.registrarNuevoTurno(
            dto.getFecha(),
            dto.getHoraInicio(),
            dto.getHoraFin(),
            dto.getPacienteId(),
            dto.getMedicoId(),
            dto.getConsultorioId());
    return Response.ok(turno, "Turno reservado. Tenés 15 minutos para confirmar.");
  }

  @RequestMapping(value = "/id/{id}/cancelar-reserva", method = RequestMethod.PATCH)
  public ResponseEntity<Object> cancelarReserva(
      @PathVariable("id") int id, @RequestBody Paciente aPaciente) {
    service.cancelarReserva(id, aPaciente.getId());
    return Response.ok(null, "Reserva deshecha correctamente.");
  }

  @RequestMapping(value = "/id/{id}/confirmar", method = RequestMethod.PATCH)
  public ResponseEntity<Object> confirmar(
      @PathVariable("id") int id,
      @RequestBody Paciente aPaciente,
      @RequestParam(required = false, defaultValue = "false") boolean forzar) {
    TurnoConfirmacionResultado resultado = service.confirmar(id, aPaciente, forzar);
    return Response.ok(resultado, "Turno confirmado correctamente");
  }
}
