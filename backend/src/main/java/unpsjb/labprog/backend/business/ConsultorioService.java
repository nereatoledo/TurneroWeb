package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import unpsjb.labprog.backend.model.Consultorio;

@Service
public class ConsultorioService {

    @Autowired
    ConsultorioRepository repository;

    @Transactional
    public Consultorio save(Consultorio consultorio) {
        return repository.save(consultorio);
    }

    public boolean existeConsultorioEnCentro(Integer idCentro, Integer numero) {
        return repository.existeConsultorioEnCentro(idCentro, numero);
    }

    public boolean existeNombreEnCentro(Integer idCentro, String nombre) {
        return repository.existeNombreEnCentro(idCentro, nombre);
    }

    public List<Consultorio> search(String term) {
        return repository.search("%" + term.toUpperCase() + "%");
    }

    public List<Consultorio> findAll() {
        List<Consultorio> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public Page<Consultorio> findByPage(int page, int size) {
        return repository.findAll(
                PageRequest.of(page, size));
    }

    public Consultorio findById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Page<Consultorio> findByCentroId(int centroId, int page, int size) {
        return repository.findByCentroId(centroId, PageRequest.of(page, size));
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

}