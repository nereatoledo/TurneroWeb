package unpsjb.labprog.backend.business;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import unpsjb.labprog.backend.model.CentroAtencion;

@Repository
public interface CentroAtencionRepository extends CrudRepository<CentroAtencion, Integer>, PagingAndSortingRepository<CentroAtencion, Integer> {

    Optional<CentroAtencion> findByNombreAndDireccion(String nombre, String direccion);

}