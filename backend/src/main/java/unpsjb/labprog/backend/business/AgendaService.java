package unpsjb.labprog.backend.business;

import java.time.LocalDate;
import java.time.LocalTime;
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
import unpsjb.labprog.backend.model.AgendaDTO;
import unpsjb.labprog.backend.model.AgendaDTO.ItemGeneracionAgenda;
import unpsjb.labprog.backend.model.CentroAtencion;
import unpsjb.labprog.backend.model.Consultorio;
import unpsjb.labprog.backend.model.DiaSemana;
import unpsjb.labprog.backend.model.EsquemaTurno;
import unpsjb.labprog.backend.model.EstadoTurno;
import unpsjb.labprog.backend.model.StaffMedico;
import unpsjb.labprog.backend.presenter.dto.AutoAgendaRequestDTO;

@Service
public class AgendaService {
  private final int DEFAULT_CALENDAR_SPAN = 6;
  @Autowired
  CentroAtencionService centroAtencionService;
  @Autowired
  TurnoService turnoService;
  @Autowired
  AgendaGenerator generator;
  @Autowired
  EsquemaTurnoService esquemaTurnoService;
  @Autowired
  ConsultorioRepository consultorioRepository;
  @Autowired
  StaffMedicoRepository staffMedicoRepository;
  @Autowired
  EsquemaTurnoRepository esquemaTurnoRepository;

  private List<LocalDate> fechasEntre(LocalDate diaInicio, LocalDate diaFin) {
    List<LocalDate> fechas = new ArrayList<>();
    LocalDate hoy = diaInicio;
    while (!hoy.isAfter(diaFin)) {
      fechas.add(hoy);
      hoy = hoy.plusDays(1);
    }
    return fechas;
  }

  private Map<DiaSemana, Collection<EsquemaTurno>> esquemaTurnosPorDiaSemana(
      Collection<EsquemaTurno> someEsquemaTurnos) {
    Map<DiaSemana, Collection<EsquemaTurno>> clasificados = new HashMap<>();
    for (EsquemaTurno aEsquemaTurno : someEsquemaTurnos) {
      if (clasificados.get(aEsquemaTurno.getDiaSemana()) == null) {
        Collection<EsquemaTurno> lista = new ArrayList<>();
        lista.add(aEsquemaTurno);
        clasificados.put(aEsquemaTurno.getDiaSemana(), lista);
      } else {
        clasificados.get(aEsquemaTurno.getDiaSemana()).add(aEsquemaTurno);
      }
    }
    return clasificados;
  }

  private Map<LocalDate, Collection<ItemGeneracionAgenda>> generarItemsDeCentro(
      CentroAtencion aCentroAtencion,
      Collection<EsquemaTurno> esquemaTurnoSeleccionados,
      LocalDate fechaInicio,
      LocalDate fechaFin) {
    Map<DiaSemana, Collection<EsquemaTurno>> mapEsquemaTurnos = esquemaTurnosPorDiaSemana(esquemaTurnoSeleccionados);
    Map<LocalDate, Collection<ItemGeneracionAgenda>> someItemGeneracionAgenda = new HashMap<>();
    for (LocalDate fecha : fechasEntre(fechaInicio, fechaFin)) {
      Collection<EsquemaTurno> esquemaTurnosHoy = mapEsquemaTurnos.get(DiaSemana.from(fecha.getDayOfWeek()));
      Collection<ItemGeneracionAgenda> itemsHoy = new ArrayList<>();
      if (esquemaTurnosHoy == null)
        continue;
      for (EsquemaTurno esquemaTurnoHoy : esquemaTurnosHoy) {
        itemsHoy.add(
            new AgendaDTO().new ItemGeneracionAgenda(
                fecha,
                esquemaTurnoHoy.getDiaSemana(),
                esquemaTurnoHoy.getConsultorio(),
                esquemaTurnoHoy.getHoraInicio(),
                esquemaTurnoHoy.getHoraFin(),
                esquemaTurnoHoy.getIntervalo(),
                aCentroAtencion,
                esquemaTurnoHoy.getStaffMedico().getMedico(),
                turnoService.find(
                    fecha,
                    esquemaTurnoHoy.getConsultorio().getId(),
                    null,
                    null,
                    List.of(
                        EstadoTurno.PROGRAMADO, EstadoTurno.CONFIRMADO, EstadoTurno.REAGENDADO))));
      }
      someItemGeneracionAgenda.put(fecha, itemsHoy);
    }
    return someItemGeneracionAgenda;
  }

  private Map<LocalDate, Collection<ItemGeneracionAgenda>> obtenerItems(
      Integer aEspecialidadId,
      Integer aMedicoId,
      Integer aCentroAtencionId,
      LocalDate fechaInicio,
      LocalDate fechaFin) {
    Map<LocalDate, Collection<ItemGeneracionAgenda>> someItems = new HashMap<>();
    if (aCentroAtencionId == null) {
      for (CentroAtencion aCentroAtencion : centroAtencionService.findAll()) {
        Collection<EsquemaTurno> esquemaTurnos = esquemaTurnoService.search(aEspecialidadId, aMedicoId,
            aCentroAtencion.getId());
        someItems.putAll(
            generarItemsDeCentro(aCentroAtencion, esquemaTurnos, fechaInicio, fechaFin));
      }
    } else {
      Collection<EsquemaTurno> esquemaTurnos = esquemaTurnoService.search(aEspecialidadId, aMedicoId,
          aCentroAtencionId);
      someItems.putAll(
          generarItemsDeCentro(
              centroAtencionService.findById(aCentroAtencionId),
              esquemaTurnos,
              fechaInicio,
              fechaFin));
    }
    return someItems;
  }

  private AgendaDTO generarSugerencias(
      Integer aEspecialidadId,
      Integer aMedicoId,
      Integer aCentroAtencionId,
      LocalDate fechaInicio,
      LocalDate fechaFin) {
    Map<LocalDate, Collection<ItemGeneracionAgenda>> items;
    AgendaDTO agenda;
    items = obtenerItems(aEspecialidadId, aMedicoId, null, fechaInicio, fechaFin);
    agenda = generator.generate(fechaInicio, fechaFin, items);
    if (!agenda.isEmpty()) {
      agenda.setObservaciones(
          "No se encontraron turnos disponibles, mostrando sugerencias con otros centros de"
              + " atención");
      return agenda;
    }
    items = obtenerItems(aEspecialidadId, null, aCentroAtencionId, fechaInicio, fechaFin);
    agenda = generator.generate(fechaInicio, fechaFin, items);
    if (!agenda.isEmpty()) {
      agenda.setObservaciones(
          "No se encontraron turnos disponibles, mostrando sugerencias con otros médicos");
      return agenda;
    }
    items = obtenerItems(aEspecialidadId, null, null, fechaInicio, fechaFin);
    agenda = generator.generate(fechaInicio, fechaFin, items);
    if (!agenda.isEmpty()) {
      agenda.setObservaciones(
          "No se encontraron turnos disponibles, mostrando sugerencias con otros médicos y"
              + " centros");
      return agenda;
    }
    agenda.setObservaciones("Sin turnos encontrados");
    return agenda;
  }

  public AgendaDTO generate(
      Integer aEspecialidadId,
      Integer aMedicoId,
      Integer aCentroAtencionId,
      LocalDate fechaInicio,
      LocalDate fechaFin) {
    if (fechaInicio == null)
      fechaInicio = LocalDate.now();
    if (fechaFin == null)
      fechaFin = fechaInicio.plusDays(DEFAULT_CALENDAR_SPAN);
    Map<LocalDate, Collection<ItemGeneracionAgenda>> items = obtenerItems(aEspecialidadId, aMedicoId, aCentroAtencionId,
        fechaInicio, fechaFin);
    AgendaDTO agenda = generator.generate(fechaInicio, fechaFin, items);
    if (!agenda.isEmpty()) {
      agenda.setObservaciones("Turnos disponibles encontrados");
      return agenda;
    }
    agenda = generarSugerencias(aEspecialidadId, aMedicoId, aCentroAtencionId, fechaInicio, fechaFin);
    return agenda;
  }

  @Transactional
  public List<EsquemaTurno> autoAsignarAgenda(AutoAgendaRequestDTO dto) {
    StaffMedico staffMedico = staffMedicoRepository.findByCentroIdYMedicoId(dto.getIdCentro(), dto.getIdMedico());
    if (staffMedico == null) {
      throw new IllegalArgumentException(
          "El médico no está asociado al centro de atención indicado.");
    }
    List<Consultorio> consultoriosCentro = consultorioRepository.findByCentroIdOrdenados(dto.getIdCentro());
    if (consultoriosCentro.isEmpty()) {
      throw new IllegalArgumentException("El centro de atención no tiene consultorios.");
    }
    List<EsquemaTurno> esquemasGuardados = new ArrayList<>();
    Set<DiaSemana> diasProcesados = new HashSet<>();
    LocalDate diaActual = dto.getFechaInicio();
    while (!diaActual.isAfter(dto.getFechaFin()) && diasProcesados.size() < 7) {
      DiaSemana diaJava = DiaSemana.from(diaActual.getDayOfWeek());
      if (diaJava != null && !diasProcesados.contains(diaJava)) {
        boolean hayConflictoMedico = esquemaTurnoRepository.existeConflictoParaMedico(
            staffMedico.getId(), diaJava, dto.getHoraInicio(), dto.getHoraFin());
        if (hayConflictoMedico) {
          throw new IllegalArgumentException(
              "El médico ya está asignado en otro consultorio en el día " + diaJava);
        }
        LocalTime currentInicio = dto.getHoraInicio();
        List<Consultorio> consultoriosDisponibles = new ArrayList<>(consultoriosCentro);
        while (currentInicio.isBefore(dto.getHoraFin())) {
          Consultorio mejorConsultorio = null;
          LocalTime mejorFinLibre = currentInicio;
          for (Consultorio c : consultoriosDisponibles) {
            LocalTime finLibre = calcularBloqueLibre(c, diaJava, currentInicio, dto.getHoraFin());
            if (finLibre.isAfter(mejorFinLibre)) {
              mejorFinLibre = finLibre;
              mejorConsultorio = c;
            }
            if (finLibre.equals(dto.getHoraFin())) {
              mejorConsultorio = c;
              mejorFinLibre = finLibre;
              break;
            }
          }
          if (mejorConsultorio == null || mejorFinLibre.equals(currentInicio)) {
            throw new IllegalStateException(
                "No hay consultorios disponibles para cubrir el bloque horario solicitado en el día"
                    + " "
                    + diaJava);
          }
          EsquemaTurno esquema = new EsquemaTurno();
          esquema.setNombre(
              dto.getNombre() != null ? dto.getNombre() : "Agenda Semanal Automática");
          esquema.setDescripcion(dto.getDescripcion());
          esquema.setDiaSemana(diaJava);
          esquema.setHoraInicio(currentInicio);
          esquema.setHoraFin(mejorFinLibre);
          esquema.setConsultorio(mejorConsultorio);
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

          esquemasGuardados.add(esquemaTurnoRepository.save(esquema));
          currentInicio = mejorFinLibre;
          consultoriosDisponibles.remove(mejorConsultorio);
        }
        diasProcesados.add(diaJava);
      }
      diaActual = diaActual.plusDays(1);
    }
    return esquemasGuardados;
  }

  private LocalTime calcularBloqueLibre(
      Consultorio consultorio, DiaSemana dia, LocalTime inicioBuscado, LocalTime finBuscado) {
    List<EsquemaTurno> esquemas = esquemaTurnoRepository.findByConsultorioIdYDia(consultorio.getId(), dia);
    LocalTime maxLibre = finBuscado;
    for (EsquemaTurno eq : esquemas) {
      if (inicioBuscado.isBefore(eq.getHoraFin()) && eq.getHoraInicio().isBefore(finBuscado)) {
        if (!inicioBuscado.isBefore(eq.getHoraInicio())) {
          return inicioBuscado;
        } else {
          if (eq.getHoraInicio().isBefore(maxLibre)) {
            maxLibre = eq.getHoraInicio();
          }
        }
      }
    }
    return maxLibre;
  }
}