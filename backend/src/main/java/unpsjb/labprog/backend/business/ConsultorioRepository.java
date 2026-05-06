package unpsjb.labprog.backend.business;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import unpsjb.labprog.backend.model.Consultorio;

@Repository
public interface ConsultorioRepository
        extends CrudRepository<Consultorio, Integer>, PagingAndSortingRepository<Consultorio, Integer> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Consultorio c WHERE (c.numero) = (?1)")
    boolean existeNumero(Integer numero);

    
}