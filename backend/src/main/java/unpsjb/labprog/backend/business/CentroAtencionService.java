package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import unpsjb.labprog.backend.model.CentroAtencion;
import unpsjb.labprog.backend.model.Especialidad;

@Service
public class CentroAtencionService {

    @Autowired
    CentroAtencionRepository repository;

    @Autowired
    EspecialidadRepository especialidadRepository;

    @Transactional
    public CentroAtencion save(CentroAtencion centro) {
        return repository.save(centro);
    }

    public List<CentroAtencion> search(String term) {
        return repository.search("%" + term.toUpperCase() + "%");
    }

    public boolean existeConflictoNombreDireccion(String nombre, String direccion) {
        return repository.existeConflictoNombreDireccion(nombre, direccion);
    }

    public boolean existeDireccion(String direccion) {
        return repository.existeDireccion(direccion);
    }

    public List<CentroAtencion> findAll() {
        List<CentroAtencion> result = new ArrayList<>();
        repository.findAll().forEach(e -> result.add(e));
        return result;
    }

    public Page<CentroAtencion> findByPage(int page, int size) {
        return repository.findAll(
                PageRequest.of(page, size));
    }

    public CentroAtencion findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    public CentroAtencion findCentroByConsultorioId(Integer idConsultorio) {
        return repository.findCentroByConsultorioId(idConsultorio);
    }

    @Transactional
    public CentroAtencion asociarEspecialidad(int idCentro, int idEspecialidad) {
        CentroAtencion centro = repository.findById(idCentro).orElse(null);
        if (centro == null) {
            throw new IllegalArgumentException("No existe el Centro Médico");
        }

        Especialidad especialidad = especialidadRepository.findById(idEspecialidad).orElse(null);
        if (especialidad == null) {
            throw new IllegalArgumentException("No existe la especialidad");
        }

        boolean yaEstaAsociada = centro.getEspecialidades().stream()
                .anyMatch(e -> e.getId() == idEspecialidad);

        if (yaEstaAsociada) {
            throw new IllegalStateException("Especialidad ya se encuentra asociada");
        }

        centro.agregarEspecialidad(especialidad);
        return repository.save(centro);
    }

    @Transactional
    public CentroAtencion desasociarEspecialidad(int idCentro, int idEspecialidad) {
        CentroAtencion centro = repository.findById(idCentro).orElse(null);
        Especialidad especialidad = especialidadRepository.findById(idEspecialidad).orElse(null);

        if (centro != null && especialidad != null) {
            if (especialidad.getMedicos() != null && !especialidad.getMedicos().isEmpty()) {
                throw new IllegalStateException(
                        "No se puede desasociar la especialidad porque hay médicos activos o turnos programados.");
            }

            centro.removerEspecialidad(especialidad);
            return repository.save(centro);
        }
        return null;
    }

    public List<CentroAtencion> findCentrosByEspecialidadId(int idEspecialidad) {
        return repository.findCentrosByEspecialidadId(idEspecialidad);
    }

    public java.util.List<java.util.Map<String, Object>> obtenerEspecialidadesPorCentro() {
        java.util.List<CentroAtencion> centros = new java.util.ArrayList<>();
        repository.findAll().forEach(centros::add);

        java.util.List<java.util.Map<String, Object>> resultado = new java.util.ArrayList<>();
        
        for (CentroAtencion c : centros) {
            if (c.getEspecialidades() != null && !c.getEspecialidades().isEmpty()) {
                java.util.Map<String, Object> map = new java.util.LinkedHashMap<>();
                map.put("centro_de_atencion", c.getNombre());
                
                java.util.List<String> especialidades = c.getEspecialidades().stream()
                        .map(Especialidad::getNombre)
                        .collect(java.util.stream.Collectors.toList());
                        
                map.put("especialidades", especialidades);
                resultado.add(map);
            }
        }
        return resultado;
    }

    public java.util.List<String> obtenerEspecialidadesDeCentro(int idCentro) {
        CentroAtencion centro = repository.findById(idCentro).orElse(null);
        if (centro == null) {
            throw new IllegalArgumentException("No existe el Centro Médico");
        }
        
        return centro.getEspecialidades().stream()
                .map(Especialidad::getNombre)
                .collect(java.util.stream.Collectors.toList());
    }
}