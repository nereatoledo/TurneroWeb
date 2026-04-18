package unpsjb.labprog.backend.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.CentroAtencionService;
import unpsjb.labprog.backend.model.CentroAtencion;

@RestController
@RequestMapping("centros")
public class CentroAtencionPresenter {

    @Autowired
    CentroAtencionService service;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> crearCentro(@RequestBody CentroAtencion centro) {
        
        // 1. Validaciones de campos requeridos (Status 400)
        if (centro.getNombre() == null || centro.getNombre().trim().isEmpty()) {
            return Response.response(HttpStatus.BAD_REQUEST, "El nombre es requerido", null);
        }
        if (centro.getDireccion() == null || centro.getDireccion().trim().isEmpty()) {
            return Response.response(HttpStatus.BAD_REQUEST, "La dirección es requerida", null);
        }
        
        // Validación simple para rechazar letras en las coordenadas (como pide tu ejemplo "abc, xyz")
        if (centro.getCoordenadas() == null || centro.getCoordenadas().matches(".*[a-zA-Z].*")) {
            return Response.response(HttpStatus.BAD_REQUEST, "Las coordenadas son inválidas", null);
        }

        // 2. Validación de Conflictos en la Base de Datos (Status 409)
        if (service.existeConflicto(centro.getNombre(), centro.getDireccion())) {
            return Response.response(HttpStatus.CONFLICT, "Ya existe un centro de atención con ese nombre y dirección", null);
        }

        // 3. Camino feliz: Todo está bien, lo guardamos (Status 200)
        CentroAtencion centroGuardado = service.save(centro);
        return Response.response(HttpStatus.OK, "Centro de atención creado", centroGuardado);
    }
}