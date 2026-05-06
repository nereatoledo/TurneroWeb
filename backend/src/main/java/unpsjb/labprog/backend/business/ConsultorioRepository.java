package unpsjb.labprog.backend.business;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import unpsjb.labprog.backend.model.Consultorio;

@Repository
public interface ConsultorioRepository
        extends CrudRepository<Consultorio, Integer>, PagingAndSortingRepository<Consultorio, Integer> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            "FROM CentroAtencion ca JOIN ca.consultorios c " +
            "WHERE ca.id = ?1 AND c.numero = ?2")
    boolean existeConsultorioEnCentro(Integer idCentro, Integer numeroConsultorio);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            "FROM CentroAtencion ca JOIN ca.consultorios c " +
            "WHERE ca.id = ?1 AND UPPER(c.nombre) = UPPER(?2)")
    boolean existeNombreEnCentro(Integer idCentro, String nombre);

    @Query("SELECT e FROM Consultorio e WHERE UPPER(e.nombre) LIKE ?1")
    List<Consultorio> search(String term);
}