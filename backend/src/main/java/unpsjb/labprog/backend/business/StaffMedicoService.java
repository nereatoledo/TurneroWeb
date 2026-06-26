package unpsjb.labprog.backend.business;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unpsjb.labprog.backend.model.StaffMedico;

@Service
public class StaffMedicoService {

  @Autowired
  StaffMedicoRepository repository;

  public StaffMedico findById(int id) {
    return repository.findById(id).orElse(null);
  }

  public Collection<StaffMedico> findByCentroAndEspecialidad(int centroId, int especialidadId) {
    return repository.findByCentroAndEspecialidad(centroId, especialidadId);
  }
}