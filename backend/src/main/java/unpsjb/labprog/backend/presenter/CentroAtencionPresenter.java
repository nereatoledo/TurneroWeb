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

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Object> crearCentro(@RequestBody CentroAtencion centro) {
        
        if (centro.getNombre() == null || centro.getNombre().trim().isEmpty()) {
            return Response.response(HttpStatus.BAD_REQUEST, "El nombre es requerido", null);
        }
        
        if (centro.getDireccion() == null || centro.getDireccion().trim().isEmpty()) {
            return Response.response(HttpStatus.BAD_REQUEST, "La dirección es requerida", null);
        }

        if (centro.getTelefono() == null || centro.getTelefono().trim().isEmpty()) {
            return Response.response(HttpStatus.BAD_REQUEST, "El teléfono es requerido", null);
        }

        if (centro.getCoordenadas() == null) {
            return Response.response(HttpStatus.BAD_REQUEST, "Las coordenadas son inválidas", null);
        }

        if (service.existeConflictoNombreDireccion(centro.getNombre(), centro.getDireccion())) {
            return Response.response(HttpStatus.CONFLICT, "Ya existe un centro de atención con ese nombre y dirección", null);
        }

        if (service.existeDireccion(centro.getDireccion())) {
            return Response.response(HttpStatus.CONFLICT, "Ya existe un centro de atención con esa dirección", null);
        }

        CentroAtencion centroGuardado = service.save(centro);
        return Response.response(HttpStatus.OK, "Centro de atención creado", centroGuardado);
    }
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Object> findAll() {
        return Response.ok(service.findAll());
    }

    @RequestMapping(value="/id/{id}", method=RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable("id") int id) {
        CentroAtencion aCentroOrNull = service.findById(id);
        return (aCentroOrNull != null)?
            Response.ok(aCentroOrNull):
            Response.notFound("Centro de Atención id " + id + " no encontrado.");
    }
    
    @RequestMapping(value = "/search/{term}", method = RequestMethod.GET)
    public ResponseEntity<Object> search(@PathVariable("term") String term) {
        return Response.ok(service.search(term));
    }

        @RequestMapping(value="/page", method=RequestMethod.GET)
    public ResponseEntity<Object> findByPage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
            return Response.ok(service.findByPage(page, size));
    }
}