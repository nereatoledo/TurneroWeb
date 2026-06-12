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

        @Query("SELECT DISTINCT m FROM StaffMedico sm JOIN sm.medico m WHERE " +
               "(UPPER(m.nombre) LIKE ?1 OR UPPER(m.apellido) LIKE ?1) " +
               "AND (?2 IS NULL OR m.especialidad.id = ?2)")
        List<Medico> search(String term, Integer especialidadId);

        @Query("SELECT e FROM Medico e WHERE e.dni = ?1")
        Medico findByDni(String dni);

        @Query("SELECT e FROM Medico e WHERE e.matricula = ?1")
        Medico findByMatricula(String matricula);

}