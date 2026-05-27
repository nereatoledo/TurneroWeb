package unpsjb.labprog.backend.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.EsquemaTurnoService;
import unpsjb.labprog.backend.business.StaffMedicoService;
import unpsjb.labprog.backend.model.EsquemaTurno;
import unpsjb.labprog.backend.model.StaffMedico;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("esquemas")
public class EsquemaTurnoPresenter {

    @Autowired
    EsquemaTurnoService service;

    @Autowired
    StaffMedicoService staffService; 

    @RequestMapping(value = "/generar", method = RequestMethod.POST)
    public ResponseEntity<Object> generarAgenda(@RequestBody Map<String, Object> payload) {
        try {
            if (!payload.containsKey("id_staff") || payload.get("id_staff") == null) {
                return Response.response(HttpStatus.BAD_REQUEST, "Debe especificar el id_staff del médico.", null);
            }
            
            int idStaff = (Integer) payload.get("id_staff");

            // 1. Recuperar el objeto persistido real de la base de datos
            StaffMedico staff = staffService.findById(idStaff);
            
            // 2. Validar que exista la relación del médico con el centro
            if (staff == null) {
                return Response.notFound("El Staff Médico con ID " + idStaff + " no existe en el sistema.");
            }

            // 3. Ejecutamos el servicio pasándole solo el staff
            List<EsquemaTurno> esquemasCreados = service.generarAgendaDinamica(staff);
            
            return Response.response(HttpStatus.OK, "Agenda de esquemas generada con éxito", esquemasCreados);

        } catch (IllegalStateException e) {
            // Captura la excepción de negocio si se agotan los consultorios o hay algún conflicto
            return Response.response(HttpStatus.CONFLICT, e.getMessage(), null);
        } catch (Exception e) {
            return Response.response(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno al procesar la agenda", null);
        }
    }
}