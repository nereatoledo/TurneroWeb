package unpsjb.labprog.backend.business;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unpsjb.labprog.backend.model.Paciente;

import java.util.Optional;

@Repository
public interface PacienteRepository extends CrudRepository<Paciente, Integer> {
    
    @Query("SELECT p FROM Paciente p WHERE p.username = :username")
    Optional<Paciente> findByUsername(@Param("username") String username);
    
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Paciente p WHERE p.username = :username")
    boolean existsByUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Paciente p WHERE p.dni = :dni")
    boolean existsByDni(@Param("dni") String dni);
}