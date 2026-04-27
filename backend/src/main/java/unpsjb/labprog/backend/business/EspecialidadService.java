package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import unpsjb.labprog.backend.model.Especialidad;

@Service
public class EspecialidadService {

    @Autowired
    EspecialidadRepository repository;

    public Especialidad save(Especialidad especialidad) {
        return repository.save(especialidad);
    }

    public List<Especialidad> search(String term) {
        return repository.search("%" + term.toUpperCase() + "%");
    }

    public List<Especialidad> findAll() {
        List<Especialidad> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public Page<Especialidad> findByPage(int page, int size) {
        return repository.findAll(
                PageRequest.of(page, size));
    }

    public boolean existeNombre(String nombre) {
        return repository.existeNombre(nombre);
    }

    public Especialidad findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

}