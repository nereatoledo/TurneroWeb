package unpsjb.labprog.backend.business;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unpsjb.labprog.backend.model.ModificacionTurno;

@Repository
public interface ModificacionTurnoRepository extends JpaRepository<ModificacionTurno, Integer> {
    java.util.List<ModificacionTurno> findByTurnoIdOrderByFechaModificacionDesc(int turnoId);
}
