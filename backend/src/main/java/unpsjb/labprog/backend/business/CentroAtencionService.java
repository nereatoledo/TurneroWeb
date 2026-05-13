package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import unpsjb.labprog.backend.model.CentroAtencion;

@Service
public class CentroAtencionService {

    @Autowired
    CentroAtencionRepository repository;

    @Transactional
    public CentroAtencion save(CentroAtencion centro) {
        return repository.save(centro);
    }

    public List<CentroAtencion> search(String term){
        return repository.search("%" + term.toUpperCase() + "%");
    }

    public boolean existeConflictoNombreDireccion(String nombre, String direccion) {
        return repository.existeConflictoNombreDireccion(nombre, direccion);
    }

    public boolean existeDireccion(String direccion) {
        return repository.existeDireccion(direccion);
    }

    public List<CentroAtencion> findAll(){
        List<CentroAtencion> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
    return result;
    }

    public Page<CentroAtencion> findByPage(int page, int size){
        return repository.findAll(
            PageRequest.of(page, size));
    }

    public CentroAtencion findById(int id){
        return repository.findById(id).orElse(null);
    }
    @Transactional
    public void delete(int id){
        repository.deleteById(id);
    }
    
    public CentroAtencion findCentroByConsultorioId(Integer idConsultorio) {
    return repository.findCentroByConsultorioId(idConsultorio);
}
}