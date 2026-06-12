package unpsjb.labprog.backend.business;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import unpsjb.labprog.backend.model.CentroAtencion;
import unpsjb.labprog.backend.model.Especialidad;
import unpsjb.labprog.backend.model.Medico;
import unpsjb.labprog.backend.model.StaffMedico;

@Service
public class CentroAtencionService {

    @Autowired
    CentroAtencionRepository repository;

    @Autowired
    EspecialidadRepository especialidadRepository;

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    StaffMedicoRepository staffMedicoRepository;

    @Transactional
    public CentroAtencion save(CentroAtencion centro) {
        return repository.save(centro);
    }

    public List<CentroAtencion> search(String term, Integer medicoId, Integer especialidadId) {
        return repository.search("%" + term.toUpperCase() + "%", medicoId, especialidadId);
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
        return repository.findAll(PageRequest.of(page, size));
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

    @Transactional
    public void asociarMedico(String nombreCentro, String dni, String matricula) {
        CentroAtencion centro = repository.findByNombre(nombreCentro);
        if (centro == null) {
            throw new IllegalArgumentException("No existe el Centro Médico");
        }

        Medico medico = medicoRepository.findByDni(dni);
        if (medico == null) {
            throw new IllegalStateException("Médico no existe con el dni indicado");
        }

        if (!medico.getMatricula().equals(matricula)) {
            throw new IllegalStateException("Médico no existe con la matrícula indicada");
        }

        boolean especialidadDisponible = centro.getEspecialidades().stream()
                .anyMatch(e -> e.getNombre().equalsIgnoreCase(medico.getEspecialidad().getNombre()));
        if (!especialidadDisponible) {
            throw new IllegalStateException(
                    "La especialidad del médico no se encuentra disponible para el centro de salud");
        }

        boolean yaEstaAsociado = staffMedicoRepository.existeMedicoEnCentro(nombreCentro, medico.getId());
        if (yaEstaAsociado) {
            throw new IllegalStateException("Médico en centro ya está asociado al centro de atención");
        }

        StaffMedico nuevoStaff = new StaffMedico();
        nuevoStaff.setCentro(centro);
        nuevoStaff.setMedico(medico);
        staffMedicoRepository.save(nuevoStaff);
    }

    @Transactional
    public void desasociarMedico(String nombreCentro, int idMedico) {
        StaffMedico staff = staffMedicoRepository.findByCentroNombreYMedicoId(nombreCentro, idMedico);
        if (staff == null) {
            throw new IllegalStateException("El médico no pertenece al staff de este centro");
        }
        staffMedicoRepository.delete(staff);
    }

    public List<Map<String, Object>> obtenerEspecialidadesPorCentro() {
        List<CentroAtencion> centros = repository.findAllConEspecialidades();

        return centros.stream()
                .map(c -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("centro_de_atencion", c.getNombre());

                    List<String> especialidadesNombres = c.getEspecialidades().stream()
                            .map(Especialidad::getNombre)
                            .collect(Collectors.toList());

                    map.put("especialidades", especialidadesNombres);
                    return map;
                })
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> obtenerEspecialidadesDeCentro(int idCentro) {
        CentroAtencion centro = repository.findById(idCentro)
                .orElseThrow(() -> new IllegalArgumentException("No existe el Centro Médico"));

        return centro.getEspecialidades().stream()
                .map(e -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("id", e.getId());
                    map.put("nombre", e.getNombre());
                    return map;
                })
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> obtenerMedicosDeCentro(String nombreCentro) {
        List<Medico> medicos = staffMedicoRepository.findMedicosByNombreCentro(nombreCentro);

        return medicos.stream()
                .map(this::mapearMedicoAJson)
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> obtenerMedicosPorCentro() {
        List<StaffMedico> todoElStaff = staffMedicoRepository.findAllStaffConDetalles();

        Map<String, List<StaffMedico>> staffPorCentro = todoElStaff.stream()
                .collect(Collectors.groupingBy(staff -> staff.getCentro().getNombre()));

        return staffPorCentro.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("centro_de_atencion", entry.getKey());

                    List<Map<String, Object>> medicosList = entry.getValue().stream()
                            .map(staff -> mapearMedicoAJson(staff.getMedico()))
                            .collect(Collectors.toList());

                    map.put("medicos", medicosList);
                    return map;
                })
                .collect(Collectors.toList());
    }

    private Map<String, Object> mapearMedicoAJson(Medico m) {
        Map<String, Object> medicoMap = new LinkedHashMap<>();
        medicoMap.put("id", m.getId());
        medicoMap.put("nombre", m.getNombre());
        medicoMap.put("apellido", m.getApellido());
        medicoMap.put("dni", Integer.parseInt(m.getDni()));
        medicoMap.put("matricula", m.getMatricula());
        medicoMap.put("especialidad", m.getEspecialidad().getNombre());
        return medicoMap;
    }
}