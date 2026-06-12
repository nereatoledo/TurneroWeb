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

    @Transactional
    public Especialidad save(Especialidad especialidad) {
        return repository.save(especialidad);
    }

    public List<Especialidad> search(String term) {
        return search(term, null);
    }

    public List<Especialidad> search(String term, Integer centroId) {
        return repository.search("%" + term.toUpperCase() + "%", centroId);
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

public Especialidad findByNombre(String nombre) {
        return repository.findByNombre(nombre);
    }

    public Especialidad findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

}