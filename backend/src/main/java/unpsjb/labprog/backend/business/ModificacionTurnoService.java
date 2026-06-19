package unpsjb.labprog.backend.business;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unpsjb.labprog.backend.model.ModificacionTurno;

@Service
public class ModificacionTurnoService {

    @Autowired
    private ModificacionTurnoRepository repository;

    public List<ModificacionTurno> obtenerHistorialPorTurno(int turnoId) {
        return repository.findByTurnoIdOrderByFechaModificacionDesc(turnoId);
    }
}
