package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import unpsjb.labprog.backend.model.Medico;

@Service
public class MedicoService {

    @Autowired
    MedicoRepository repository;

    @Transactional
    public Medico save(Medico medico) {
        return repository.save(medico);
    }

    public List<Medico> search(String term, Integer especialidadId, Integer centroId) {
        return repository.search("%" + term.toUpperCase() + "%", especialidadId, centroId);
    }

    public List<Medico> findAll() {
        List<Medico> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public Page<Medico> findByPage(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    public Medico findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    public Medico findByDni(String dni) {
        return repository.findByDni(dni);
    }

    public Medico findByMatricula(String matricula) {
        return repository.findByMatricula(matricula);
    }
}