package unpsjb.labprog.backend.business;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import unpsjb.labprog.backend.model.Medico;

@Repository
public interface MedicoRepository
                extends CrudRepository<Medico, Integer>, PagingAndSortingRepository<Medico, Integer> {

        @Query("SELECT e FROM Medico e WHERE UPPER(e.nombre) LIKE ?1")
        List<Medico> search(String term);

}