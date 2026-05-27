package unpsjb.labprog.backend.business;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import unpsjb.labprog.backend.model.Medico;
import unpsjb.labprog.backend.model.StaffMedico;

import java.util.List;

@Repository
public interface StaffMedicoRepository 
        extends CrudRepository<StaffMedico, Integer>, PagingAndSortingRepository<StaffMedico, Integer> {

    @Query("SELECT s.medico FROM StaffMedico s WHERE s.centro.nombre = ?1")
    List<Medico> findMedicosByNombreCentro(String nombreCentro);
    
    @Query("SELECT s.medico FROM StaffMedico s WHERE s.centro.id = ?1")
    List<Medico> findMedicosByIdCentro(int idCentro);
    
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM StaffMedico s WHERE s.centro.nombre = ?1 AND s.medico.id = ?2")
    boolean existeMedicoEnCentro(String nombreCentro, int idMedico);

    @Query("SELECT s FROM StaffMedico s WHERE s.centro.nombre = ?1 AND s.medico.id = ?2")
    StaffMedico findByCentroNombreYMedicoId(String nombreCentro, int idMedico);

    @Query("SELECT s FROM StaffMedico s JOIN FETCH s.centro JOIN FETCH s.medico m LEFT JOIN FETCH m.especialidad")
    List<StaffMedico> findAllStaffConDetalles();
}