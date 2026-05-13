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

    @Query("SELECT e FROM Especialidad e WHERE UPPER(e.nombre) LIKE ?1")
    List<Especialidad> search(String term);
}