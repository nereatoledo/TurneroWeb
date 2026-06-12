package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import unpsjb.labprog.backend.model.EstadoTurno;
import unpsjb.labprog.backend.model.Turno;

@Service
public class TurnoService {

    @Autowired
    TurnoRepository repository;

    public List<Turno> findAll(){
        List<Turno> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public Turno findById(int id){
        return repository.findById(id).orElse(null);
    }
    
    @Transactional
    public Turno save(Turno e){
        return repository.save(e);
    }
    
    @Transactional
    public void delete(int id){
        repository.deleteById(id);
    }

    public List<Turno> buscarTurnosConFiltros(Integer especialidadId, Integer medicoId, Integer centroId) {
        EstadoTurno estadoLibre = EstadoTurno.PROGRAMADO; 
        return repository.buscarDisponiblesParaPaciente(estadoLibre, especialidadId, medicoId, centroId);
    }
}