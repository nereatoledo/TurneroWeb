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

    // endoints púbicos
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> crearCentro(@RequestBody CentroAtencion centro) {

        String errorCampos = validarCamposObligatorios(centro);
        if (errorCampos != null) {
            return Response.response(HttpStatus.BAD_REQUEST, errorCampos, null);
        }

        if (service.existeConflictoNombreDireccion(centro.getNombre(), centro.getDireccion())) {
            return Response.response(HttpStatus.CONFLICT, "Ya existe un centro de atención con ese nombre y dirección",
                    null);
        }
        if (service.existeDireccion(centro.getDireccion())) {
            return Response.response(HttpStatus.CONFLICT, "Ya existe un centro de atención con esa dirección", null);
        }

        CentroAtencion centroGuardado = service.save(centro);
        return Response.response(HttpStatus.OK, "Centro de atención creado", centroGuardado);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody CentroAtencion centroActualizado) {

        if (centroActualizado.getId() <= 0) {
            return Response.error(centroActualizado,
                    "Debe especificar un id válido para poder modificar un Centro de Atención.");
        }

        String errorCampos = validarCamposObligatorios(centroActualizado);
        if (errorCampos != null) {
            return Response.response(HttpStatus.BAD_REQUEST, errorCampos, null);
        }

        CentroAtencion centroExistente = service.findById(centroActualizado.getId());
        if (centroExistente == null) {
            return Response.error(centroActualizado,
                    "Debe especificar un id válido para poder modificar un Centro de Atención.");
        }

        ResponseEntity<Object> errorConflicto = validarConflictosDeEdicion(centroExistente, centroActualizado);
        if (errorConflicto != null) {
            return errorConflicto;
        }

        mapearDatos(centroExistente, centroActualizado);
        service.save(centroExistente);

        return Response.response(HttpStatus.OK, "Centro de atención modificado", centroExistente);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> findAll() {
        return Response.ok(service.findAll(), "Consulta exitosa");
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable("id") int id) {
        CentroAtencion aCentroOrNull = service.findById(id);
        return (aCentroOrNull != null) ? Response.ok(aCentroOrNull, "Consulta exitosa")
                : Response.notFound("Centro de Atención id " + id + " no encontrado.");
    }

    @RequestMapping(value = "/search/{term}", method = RequestMethod.GET)
    public ResponseEntity<Object> search(@PathVariable("term") String term) {
        return Response.ok(service.search(term));
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Object> findByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Response.ok(service.findByPage(page, size));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        service.delete(id);
        return Response.ok("Centro De Atención borrado con éxito.", "Centro De Atención borrado con éxito.");
    }

    @RequestMapping(value = "/{idCentro}/especialidades/{idEspecialidad}", method = RequestMethod.POST)
    public ResponseEntity<Object> asociarEspecialidad(@PathVariable("idCentro") int idCentro,
            @PathVariable("idEspecialidad") int idEspecialidad) {
        try {
            CentroAtencion centroActualizado = service.asociarEspecialidad(idCentro, idEspecialidad);
            return Response.response(HttpStatus.OK, "Asociación de especialidad en centro realizada correctamente",
                    centroActualizado);

        } catch (IllegalArgumentException | IllegalStateException e) {
            return Response.response(HttpStatus.CONFLICT, e.getMessage(), null);
        }
    }

    @RequestMapping(value = "/{idCentro}/especialidades/{idEspecialidad}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> desasociarEspecialidad(@PathVariable("idCentro") int idCentro,
            @PathVariable("idEspecialidad") int idEspecialidad) {
        try {
            CentroAtencion centroActualizado = service.desasociarEspecialidad(idCentro, idEspecialidad);

            if (centroActualizado == null) {
                return Response.response(HttpStatus.NOT_FOUND, "El Centro de Atención o la Especialidad no existen.",
                        null);
            }

            return Response.response(HttpStatus.OK, "Especialidad desasociada exitosamente del centro de atención.",
                    centroActualizado);

        } catch (IllegalStateException e) {
            return Response.response(HttpStatus.CONFLICT, e.getMessage(), null);
        }
    }

    @RequestMapping(value = "/especialidad/{idEspecialidad}", method = RequestMethod.GET)
    public ResponseEntity<Object> findCentrosByEspecialidad(@PathVariable("idEspecialidad") int idEspecialidad) {
        return Response.ok(service.findCentrosByEspecialidadId(idEspecialidad), "Consulta exitosa");
    }

    // métodos privados
    private String validarCamposObligatorios(CentroAtencion c) {
        if (c.getNombre() == null || c.getNombre().trim().isEmpty())
            return "El nombre es requerido";
        if (c.getDireccion() == null || c.getDireccion().trim().isEmpty())
            return "La dirección es requerida";
        if (c.getLocalidad() == null || c.getLocalidad().trim().isEmpty())
            return "La localidad es requerida";
        if (c.getProvincia() == null || c.getProvincia().trim().isEmpty())
            return "La provincia es requerida";
        if (c.getTelefono() == null || c.getTelefono().trim().isEmpty())
            return "El teléfono es requerido";
        if (c.getCoordenadas() == null)
            return "Las coordenadas son inválidas";

        if (!c.getTelefono().matches("^[0-9 \\-\\+]+$")) {
            return "El formato del teléfono es inválido. Solo se aceptan números.";
        }
        return null;
    }

    private ResponseEntity<Object> validarConflictosDeEdicion(CentroAtencion existente, CentroAtencion nuevo) {
        boolean cambioNombre = !existente.getNombre().equalsIgnoreCase(nuevo.getNombre());
        boolean cambioDireccion = !existente.getDireccion().equalsIgnoreCase(nuevo.getDireccion());
        boolean cambioLocalidad = !existente.getLocalidad().equalsIgnoreCase(nuevo.getLocalidad());
        boolean cambioProvincia = !existente.getProvincia().equalsIgnoreCase(nuevo.getProvincia());
        boolean cambioTelefono = !existente.getTelefono().equalsIgnoreCase(nuevo.getTelefono());
        boolean cambioLat = Double.compare(existente.getCoordenadas().getLatitud(),
                nuevo.getCoordenadas().getLatitud()) != 0;
        boolean cambioLon = Double.compare(existente.getCoordenadas().getLongitud(),
                nuevo.getCoordenadas().getLongitud()) != 0;

        boolean hayCambios = cambioNombre || cambioDireccion || cambioLocalidad || cambioProvincia || cambioTelefono
                || cambioLat || cambioLon;

        if (!hayCambios) {
            return Response.response(HttpStatus.CONFLICT, "Ya existe un centro de atención con ese nombre y dirección",
                    null);
        }

        if ((cambioNombre || cambioDireccion)
                && service.existeConflictoNombreDireccion(nuevo.getNombre(), nuevo.getDireccion())) {
            return Response.response(HttpStatus.CONFLICT, "Ya existe un centro de atención con ese nombre y dirección",
                    null);
        }
        if (cambioDireccion && service.existeDireccion(nuevo.getDireccion())) {
            return Response.response(HttpStatus.CONFLICT, "Ya existe un centro de atención con esa dirección", null);
        }

        return null;
    }

    private void mapearDatos(CentroAtencion existente, CentroAtencion nuevo) {
        existente.setNombre(nuevo.getNombre());
        existente.setDireccion(nuevo.getDireccion());
        existente.setLocalidad(nuevo.getLocalidad());
        existente.setProvincia(nuevo.getProvincia());
        existente.setTelefono(nuevo.getTelefono());
        existente.getCoordenadas().setLatitud(nuevo.getCoordenadas().getLatitud());
        existente.getCoordenadas().setLongitud(nuevo.getCoordenadas().getLongitud());
    }
}