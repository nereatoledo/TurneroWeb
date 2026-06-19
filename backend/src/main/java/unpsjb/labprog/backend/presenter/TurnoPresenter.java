package unpsjb.labprog.backend.presenter;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.TurnoService;
import unpsjb.labprog.backend.presenter.dto.TurnoReservaDTO;
import unpsjb.labprog.backend.model.Paciente;
import unpsjb.labprog.backend.model.Turno;
import unpsjb.labprog.backend.model.EstadoTurno;
import unpsjb.labprog.backend.presenter.dto.AgendaBusquedaResultadoDTO;
import unpsjb.labprog.backend.presenter.dto.TurnoConfirmacionResultado;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("turnos")
public class TurnoPresenter {

    @Autowired
    TurnoService service;

    @Autowired
    unpsjb.labprog.backend.business.AgendaService agendaService;

    @Autowired
    unpsjb.labprog.backend.business.ModificacionTurnoService modificacionTurnoService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> findAll() {
        return Response.ok(service.findAll(), "Turnos recuperados correctamente");
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable("id") Integer id) {
        Turno aTurno = service.findById(id);
        if (aTurno == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No existe el turno"));
        }
        return Response.ok(aTurno, "Turno recuperado");
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
        java.time.LocalDate hoy = java.time.LocalDate.now(java.time.ZoneId.of("America/Argentina/Buenos_Aires"));
        AgendaBusquedaResultadoDTO resultado = agendaService.buscarConFallback(
                hoy, hoy.plusDays(7), especialidadId, medicoId, centroId);
        return Response.ok(resultado, "Agenda recuperada con éxito");
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody Turno aTurno) {
        try {
            Turno savedTurno = service.save(aTurno);
            return Response.ok(savedTurno, "Turno Ingresado Correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "El turno no se encuentra disponible. Por favor seleccione otro horario."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Ocurrió un error inesperado al procesar la reserva."));
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody Turno aTurno) {
        try {
            service.save(aTurno);
            return Response.ok(null, "Turno Modificado Correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Conflicto de horarios. El turno ya existe en ese consultorio."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Ocurrió un error inesperado al actualizar el turno."));
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        try {
            service.delete(id);
            return Response.ok(null, "Turno cancelado exitosamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @RequestMapping(value = "/{id}/reprogramar", method = RequestMethod.GET)
    public ResponseEntity<Object> buscarParaReprogramar(@PathVariable("id") int id) {
        try {
            Turno origen = service.findById(id);
            if (origen == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Turno origen no encontrado."));
            }
            java.time.LocalDate desde = java.time.LocalDate.now(java.time.ZoneId.of("America/Argentina/Buenos_Aires"))
                    .plusDays(1);
            AgendaBusquedaResultadoDTO resultado = agendaService.buscarConFallback(
                    desde, desde.plusDays(7), null, origen.getMedico().getId(), null);
            return Response.ok(resultado, "Turnos disponibles para reprogramar");
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @RequestMapping(value = "/id/{id}/historial", method = RequestMethod.GET)
    public ResponseEntity<Object> obtenerHistorial(@PathVariable("id") int id) {
        try {
            return Response.ok(modificacionTurnoService.obtenerHistorialPorTurno(id), "Historial recuperado con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error al recuperar historial"));
        }
    }

    @RequestMapping(value = "/{id}/reprogramar", method = RequestMethod.PATCH)
    public ResponseEntity<Object> confirmarReprogramacion(
            @PathVariable("id") int id,
            @RequestBody TurnoReservaDTO dto) {
        try {
            Turno turnoActualizado = service.reprogramarTurno(
                    id,
                    dto.getFecha(),
                    dto.getHoraInicio(),
                    dto.getHoraFin(),
                    dto.getConsultorioId());
            return Response.ok(turnoActualizado, "Turno reprogramado exitosamente");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Ese horario ya no se encuentra disponible. Por favor, actualizá la agenda."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error al reprogramar el turno."));
        }
    }

    @RequestMapping(value = "/reservar", method = RequestMethod.POST)
    public ResponseEntity<Object> reservar(
            @RequestBody TurnoReservaDTO dto) {
        try {
            Turno turno = service.registrarNuevoTurno(
                    dto.getFecha(),
                    dto.getHoraInicio(),
                    dto.getHoraFin(),
                    dto.getPacienteId(),
                    dto.getMedicoId(),
                    dto.getConsultorioId());
            return Response.ok(turno, "Turno reservado. Tenés 15 minutos para confirmar.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Ese horario ya no se encuentra disponible. Por favor, actualizá la agenda."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Ocurrió un error inesperado al procesar la reserva."));
        }
    }

    @RequestMapping(value = "/id/{id}/cancelar-reserva", method = RequestMethod.PATCH)
    public ResponseEntity<Object> cancelarReserva(
            @PathVariable("id") int id,
            @RequestBody Paciente aPaciente) {
        try {
            service.cancelarReserva(id, aPaciente.getId());
            return Response.ok(null, "Reserva deshecha correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        }
    }

    @RequestMapping(value = "/id/{id}/confirmar", method = RequestMethod.PATCH)
    public ResponseEntity<Object> confirmar(
            @PathVariable("id") int id,
            @RequestBody Paciente aPaciente,
            @RequestParam(required = false, defaultValue = "false") boolean forzar) {
        try {
            TurnoConfirmacionResultado resultado = service.confirmar(id, aPaciente, forzar);
            if (resultado.isRequiereConfirmacion()) {
                return ResponseEntity.ok(Map.of(
                        "requiereConfirmacion", true,
                        "advertencia", resultado.getAdvertencia()));
            }
            return Response.ok(resultado.getTurno(), "Turno confirmado correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Error de integridad de datos al confirmar el turno."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Ocurrió un error inesperado al confirmar el turno."));
        }
    }
}