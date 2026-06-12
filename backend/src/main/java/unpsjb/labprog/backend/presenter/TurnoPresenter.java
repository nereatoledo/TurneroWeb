package unpsjb.labprog.backend.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.TurnoService;
import unpsjb.labprog.backend.model.Turno;

@RestController
@RequestMapping("turnos")
public class TurnoPresenter {

    @Autowired
    TurnoService service;

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Object> findAll(){
        return Response.ok(service.findAll(), "Turnos recuperados correctamente");
    }

    @RequestMapping(value="/id/{id}", method=RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable("id") Integer id){
        Turno aTurno = service.findById(id);
        if (aTurno == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No existe el turno"));
        }
        return Response.ok(aTurno, "Turno recuperado");
    }

    @RequestMapping(value="/disponibles", method=RequestMethod.GET)
    public ResponseEntity<Object> buscarDisponibles(
        @RequestParam(required = false, name = "especialidad_id") Integer especialidadId,
        @RequestParam(required = false, name = "medico_id") Integer medicoId,
        @RequestParam(required = false, name = "centro_id") Integer centroId
    ) {
        List<Turno> turnos = service.buscarTurnosConFiltros(especialidadId, medicoId, centroId);
        
        if (turnos == null || turnos.isEmpty()) {
            return Response.ok(new ArrayList<>(), "No se encontraron turnos disponibles con esos filtros");
        }
        return Response.ok(turnos, "Turnos recuperados con éxito");
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody Turno aTurno){
        try {
            service.save(aTurno);
            return Response.ok(null, "Turno Ingresado Correctamente");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", "El turno no se encuentra disponible. Por favor seleccione otro horario."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Ocurrió un error inesperado al procesar la reserva."));
        }
    }

    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody Turno aTurno){
        try {
            service.save(aTurno);
            return Response.ok(null, "Turno Modificado Correctamente");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", "Conflicto de horarios. El turno ya existe en ese consultorio."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Ocurrió un error inesperado al actualizar el turno."));
        }
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id){
        service.delete(id);
        return Response.ok(null, "Turno eliminado exitosamente");
    }
}