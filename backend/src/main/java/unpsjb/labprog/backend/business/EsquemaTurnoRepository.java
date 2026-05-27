package unpsjb.labprog.backend.business;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import unpsjb.labprog.backend.model.DiaSemana;
import unpsjb.labprog.backend.model.EsquemaTurno;

import java.util.List;

@Repository
public interface EsquemaTurnoRepository 
        extends CrudRepository<EsquemaTurno, Integer>, PagingAndSortingRepository<EsquemaTurno, Integer> {

    @Query("SELECT e FROM EsquemaTurno e WHERE e.staffMedico.centro.id = ?1 AND e.diaSemana = ?2")
    List<EsquemaTurno> findByCentroYDia(int idCentro, DiaSemana diaSemana);

    @Query("SELECT e FROM EsquemaTurno e WHERE e.consultorio.id = ?1")
    List<EsquemaTurno> findByConsultorioId(int idConsultorio);
}