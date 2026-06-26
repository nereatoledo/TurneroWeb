package unpsjb.labprog.backend.business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unpsjb.labprog.backend.model.CentroAtencion;
import unpsjb.labprog.backend.model.Consultorio;
import unpsjb.labprog.backend.model.DatosGeneracionHorariosDTO;
import unpsjb.labprog.backend.model.DiaSemana;
import unpsjb.labprog.backend.model.DisponibilidadMedico;
import unpsjb.labprog.backend.model.Especialidad;
import unpsjb.labprog.backend.model.EsquemaTurno;
import unpsjb.labprog.backend.model.ItemGeneracionEsquemaTurno;
import unpsjb.labprog.backend.model.StaffMedico;
import unpsjb.labprog.backend.presenter.dto.AgendaRequestDTO;

@Service
public class EsquemaTurnoService {
  @Autowired
  EsquemaTurnoRepository repository;
  @Autowired
  EsquemaTurnoGenerator generator;
  @Autowired
  CentroAtencionService centroAtencionService;
  @Autowired
  ConsultorioRepository consultorioRepository;
  @Autowired
  StaffMedicoRepository staffMedicoRepository;
  @Autowired
  StaffMedicoService staffMedicoService;

  public EsquemaTurno findById(int id) {
    return repository.findById(id).orElse(null);
  }

  private Map<Integer, Collection<ItemGeneracionEsquemaTurno>> generarItemsPorEspecialidad(
      Collection<StaffMedico> staffMedicos, Collection<Especialidad> especialidades) {
    Map<Integer, Collection<ItemGeneracionEsquemaTurno>> mapItems = new HashMap<>();
    for (Especialidad especialidad : especialidades) {
      mapItems.put(especialidad.getId(), new ArrayList<>());
    }
    for (StaffMedico aStaffMedico : staffMedicos) {
      for (DisponibilidadMedico aDisponibilidadMedico : aStaffMedico.getDisponibilidad()) {
        mapItems
            .get(aStaffMedico.getMedico().getEspecialidad().getId())
            .add(
                new ItemGeneracionEsquemaTurno(
                    aDisponibilidadMedico.getDiaSemana(),
                    aDisponibilidadMedico.getHoraInicio(),
                    aDisponibilidadMedico.getHoraFin(),
                    aStaffMedico));
      }
    }
    return mapItems;
  }

  public Collection<EsquemaTurno> generar(DatosGeneracionHorariosDTO datosGeneracion) {
    datosGeneracion.limpiarEspecialidadesSinObjetivo();
    datosGeneracion.limpiarConsultoriosSinObjetivo();
    datosGeneracion.limpiarDiasSinAtencion();
    Collection<Especialidad> especialidades = datosGeneracion.getEspecialidades();
    Collection<StaffMedico> staffMedicos = new ArrayList<>();
    for (Especialidad especialidad : especialidades) {
      staffMedicos.addAll(
          staffMedicoService.findByCentroAndEspecialidad(
              datosGeneracion.getCentroAtencionId(), especialidad.getId()));
    }
    datosGeneracion.calcularValores();
    Map<Integer, Collection<ItemGeneracionEsquemaTurno>> itemsPorEspecialidad = generarItemsPorEspecialidad(
        staffMedicos, especialidades);
    Collection<EsquemaTurno> esquemaTurnos = generator.generate(itemsPorEspecialidad, datosGeneracion);
    return esquemaTurnos;
  }

  public List<EsquemaTurno> search(
      Integer aEspecialidadId, Integer aMedicoId, Integer aCentroAtencionId) {
    if (aEspecialidadId == null) {
      throw new NullPointerException(
          "No se pueden buscar esquemas de turnos con especialidad.id null");
    }
    return repository.search(aEspecialidadId, aMedicoId, aCentroAtencionId);
  }

  @Transactional
  public void procesarYGuardarAgenda(AgendaRequestDTO dto) {
    Consultorio consultorio = consultorioRepository
        .findById(dto.getIdConsultorio())
        .orElseThrow(() -> new IllegalArgumentException("Consultorio no encontrado."));
    CentroAtencion centro = centroAtencionService.findCentroByConsultorioId(consultorio.getId());
    if (centro == null) {
      throw new IllegalArgumentException(
          "El consultorio no pertenece a ningún centro de atención.");
    }
    StaffMedico staffMedico = null;
    if (dto.getIdMedico() != null) {
      staffMedico = staffMedicoRepository.findByCentroNombreYMedicoId(centro.getNombre(), dto.getIdMedico());
      if (staffMedico == null) {
        throw new IllegalArgumentException(
            "El médico no está asociado al centro de atención de este consultorio.");
      }
    }
    Set<DiaSemana> diasProcesados = new HashSet<>();
    LocalDate diaActual = dto.getFechaInicio();
    while (!diaActual.isAfter(dto.getFechaFin()) && diasProcesados.size() < 7) {
      DiaSemana diaJava = DiaSemana.from(diaActual.getDayOfWeek());
      if (diaJava != null && !diasProcesados.contains(diaJava)) {
        boolean hayConflictoConsultorio = repository.existeConflictoEnConsultorio(
            consultorio.getId(), diaJava, dto.getHoraInicio(), dto.getHoraFin());
        if (hayConflictoConsultorio) {
          throw new IllegalArgumentException("Conflicto de horarios en el consultorio");
        }
        if (staffMedico != null) {
          boolean hayConflictoMedico = repository.existeConflictoParaMedico(
              staffMedico.getId(), diaJava, dto.getHoraInicio(), dto.getHoraFin());
          if (hayConflictoMedico) {
            throw new IllegalArgumentException("El médico ya está asignado en otro consultorio");
          }
        }
        EsquemaTurno esquema = new EsquemaTurno();
        esquema.setNombre(dto.getNombre() != null ? dto.getNombre() : "Agenda Semanal");
        esquema.setDescripcion(dto.getDescripcion());
        esquema.setDiaSemana(diaJava);
        esquema.setHoraInicio(dto.getHoraInicio());
        esquema.setHoraFin(dto.getHoraFin());
        esquema.setConsultorio(consultorio);
        esquema.setStaffMedico(staffMedico);

        Integer intervalo = 30;
        if (staffMedico != null && staffMedico.getMedico() != null
            && staffMedico.getMedico().getEspecialidad() != null) {
          Integer intervaloEsp = staffMedico.getMedico().getEspecialidad().getIntervalo();
          if (intervaloEsp != null && intervaloEsp > 0) {
            intervalo = intervaloEsp;
          }
        }
        esquema.setIntervalo(intervalo);

        repository.save(esquema);
        diasProcesados.add(diaJava);
      }
      diaActual = diaActual.plusDays(1);
    }
  }

  public void cancelarDisponibilidad(int idMedico, int idConsultorio) {
    List<EsquemaTurno> esquemas = repository.findByConsultorioId(idConsultorio);
    for (EsquemaTurno eq : esquemas) {
      if (eq.getStaffMedico() != null && eq.getStaffMedico().getMedico().getId() == idMedico) {
        repository.delete(eq);
      }
    }
  }
}