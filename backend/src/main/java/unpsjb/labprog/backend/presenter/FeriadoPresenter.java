package unpsjb.labprog.backend.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unpsjb.labprog.backend.Response;
import unpsjb.labprog.backend.business.FeriadoRepository;
import unpsjb.labprog.backend.model.Feriado;

@RestController
@RequestMapping("feriados")
public class FeriadoPresenter {

    @Autowired
    private FeriadoRepository feriadoRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> findAll() {
        return Response.response(HttpStatus.OK, "Feriados recuperados", feriadoRepository.findAll());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody Feriado feriado) {
        if (feriado.getFecha() == null) {
            return Response.response(HttpStatus.BAD_REQUEST, "La fecha es obligatoria", null);
        }
        
        if (feriadoRepository.existeFeriadoPorFecha(feriado.getFecha())) {
            return Response.response(HttpStatus.CONFLICT, "Ya existe un feriado en esa fecha", null);
        }

        if (feriado.getDescripcion() == null || feriado.getDescripcion().isEmpty()) {
            feriado.setDescripcion("Feriado Nacional / No laborable");
        }

        Feriado guardado = feriadoRepository.save(feriado);
        return Response.response(HttpStatus.OK, "Feriado registrado con éxito", guardado);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        feriadoRepository.deleteById(id);
        return Response.response(HttpStatus.OK, "Feriado eliminado", null);
    }
}