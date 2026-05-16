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

    @Query("SELECT ca FROM CentroAtencion ca JOIN ca.consultorios c WHERE c.id = ?1")
    CentroAtencion findCentroByConsultorioId(Integer idConsultorio);

    @Query("SELECT c FROM CentroAtencion c JOIN c.especialidades e WHERE e.id = ?1")
    List<CentroAtencion> findCentrosByEspecialidadId(int idEspecialidad);
}