package unpsjb.labprog.backend.business;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import unpsjb.labprog.backend.model.CentroAtencion;

@Repository
public interface CentroAtencionRepository
        extends CrudRepository<CentroAtencion, Integer>, PagingAndSortingRepository<CentroAtencion, Integer> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CentroAtencion c WHERE UPPER(c.nombre) = UPPER(?1) AND UPPER(c.direccion) = UPPER(?2)")
    boolean existeConflictoNombreDireccion(String nombre, String direccion);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CentroAtencion c WHERE UPPER(c.direccion) = UPPER(?1)")
    boolean existeDireccion(String direccion);

    @Query("SELECT e FROM CentroAtencion e WHERE UPPER(e.nombre) LIKE ?1")
    List<CentroAtencion> search(String term);

}