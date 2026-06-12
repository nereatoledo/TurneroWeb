package unpsjb.labprog.backend.business;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import unpsjb.labprog.backend.model.Especialidad;

@Repository
public interface EspecialidadRepository
        extends CrudRepository<Especialidad, Integer>, PagingAndSortingRepository<Especialidad, Integer> {

    @Query("SELECT e FROM Especialidad e WHERE UPPER(e.nombre) = UPPER(?1)")
    Especialidad findByNombre(String nombre);

    @Query("SELECT DISTINCT e FROM Especialidad e WHERE " +
           "(UPPER(e.nombre) LIKE ?1) " +
           "AND (?2 IS NULL OR EXISTS (SELECT c FROM CentroAtencion c JOIN c.especialidades ce WHERE c.id = ?2 AND ce = e))")
    List<Especialidad> search(String term, Integer centroId);
}