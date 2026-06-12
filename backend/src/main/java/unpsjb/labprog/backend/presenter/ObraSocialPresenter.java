package unpsjb.labprog.backend.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.ObraSocialRepository;
import unpsjb.labprog.backend.business.PacienteRepository;
import unpsjb.labprog.backend.business.TurnoRepository;
import unpsjb.labprog.backend.model.ObraSocial;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("obras-sociales")
public class ObraSocialPresenter {

    @Autowired
    private ObraSocialRepository obraSocialRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private TurnoRepository turnoRepository;

    @RequestMapping(value = "/clear", method = RequestMethod.DELETE)
    public ResponseEntity<Object> clear() {
        turnoRepository.deleteAll();
        pacienteRepository.deleteAll();
        obraSocialRepository.deleteAll();
        return Response.ok(null, "Base de datos reseteada para obras sociales y pacientes");
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> crearObraSocial(@RequestBody ObraSocial obraSocial) {
        if (obraSocial.getNombre() == null || obraSocial.getNombre().trim().isEmpty()) {
            return Response.response(HttpStatus.CONFLICT, "El nombre de la obra social es obligatorio", null);
        }
        if (obraSocial.getCodigo() == null || obraSocial.getCodigo().trim().isEmpty()) {
            return Response.response(HttpStatus.CONFLICT, "El codigo de la obra social es obligatorio", null);
        }
        if (obraSocialRepository.findByNombre(obraSocial.getNombre().trim()).isPresent()) {
            return Response.response(HttpStatus.CONFLICT, "Ya existe una obra social con ese nombre", null);
        }
        if (obraSocialRepository.findByCodigo(obraSocial.getCodigo().trim()).isPresent()) {
            return Response.response(HttpStatus.CONFLICT, "Ya existe una obra social con ese codigo", null);
        }

        obraSocial.setNombre(obraSocial.getNombre().trim());
        obraSocial.setCodigo(obraSocial.getCodigo().trim());
        ObraSocial guardada = obraSocialRepository.save(obraSocial);

        return Response.response(HttpStatus.OK, "Obra social creada correctamente", guardada);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> findAll() {
        List<ObraSocial> list = new ArrayList<>();
        obraSocialRepository.findAll().forEach(list::add);
        return Response.response(HttpStatus.OK, "Obras sociales recuperadas exitosamente", list);
    }
}
