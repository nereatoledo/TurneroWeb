package unpsjb.labprog.backend.business;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import unpsjb.labprog.backend.model.Feriado;

import java.time.LocalDate;

@Repository
public interface FeriadoRepository 
        extends CrudRepository<Feriado, Integer>, PagingAndSortingRepository<Feriado, Integer> {

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Feriado f WHERE f.fecha = ?1")
    boolean existeFeriadoPorFecha(LocalDate fecha);
}   