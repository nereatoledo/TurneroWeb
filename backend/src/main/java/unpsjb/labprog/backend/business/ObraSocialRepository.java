package unpsjb.labprog.backend.business;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import unpsjb.labprog.backend.model.ObraSocial;

@Repository
public interface ObraSocialRepository extends CrudRepository<ObraSocial, Integer> {
    @Query("SELECT o FROM ObraSocial o WHERE o.nombre = :nombre")
    Optional<ObraSocial> findByNombre(@Param("nombre") String nombre);

    @Query("SELECT o FROM ObraSocial o WHERE o.codigo = :codigo")
    Optional<ObraSocial> findByCodigo(@Param("codigo") String codigo);
}