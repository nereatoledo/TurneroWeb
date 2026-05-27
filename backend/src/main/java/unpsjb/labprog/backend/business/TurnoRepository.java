package unpsjb.labprog.backend.business;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unpsjb.labprog.backend.model.Turno;

@Repository
public interface TurnoRepository extends CrudRepository<Turno, Integer> {
    
    @Query("SELECT COUNT(t) FROM Turno t WHERE t.esquemaTurno.id = ?1")
    long countByEsquemaTurnoId(int esquemaTurnoId);
}