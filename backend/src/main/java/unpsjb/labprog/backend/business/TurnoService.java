package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import unpsjb.labprog.backend.model.EstadoTurno;
import unpsjb.labprog.backend.model.Paciente;
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

    /**
     * Confirma un turno de forma atómica sin SELECT previo.
     * El UPDATE solo aplica si el estado actual es PROGRAMADO o CANCELADO.
     * Si otro usuario lo tomó en el mismo instante, la BD retorna 0 filas
     * y se lanza una excepción antes de que ocurra cualquier conflicto.
     */
    @Transactional
    public Turno confirmar(int id, Paciente aPaciente) {
        int filasAfectadas = repository.confirmar(
            id,
            aPaciente,
            EstadoTurno.CONFIRMADO,
            Arrays.asList(EstadoTurno.PROGRAMADO, EstadoTurno.CANCELADO)
        );
        if (filasAfectadas == 0) {
            throw new IllegalArgumentException("El turno no se encuentra disponible. Por favor seleccione otro horario.");
        }
        return repository.findById(id).orElse(null);
    }

    public List<Turno> buscarTurnosConFiltros(Integer especialidadId, Integer medicoId, Integer centroId) {
        return repository.buscarDisponiblesParaPaciente(
            Arrays.asList(EstadoTurno.PROGRAMADO, EstadoTurno.CANCELADO),
            especialidadId, medicoId, centroId
        );
    }
}