package unpsjb.labprog.backend.business;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import unpsjb.labprog.backend.model.DatosGeneracionHorariosDTO;
import unpsjb.labprog.backend.model.DatosGeneracionHorariosDTO.DatosConsultorio;
import unpsjb.labprog.backend.model.DatosGeneracionHorariosDTO.DatosDia;
import unpsjb.labprog.backend.model.DatosGeneracionHorariosDTO.DatosEspecialidad;
import unpsjb.labprog.backend.model.DiaSemana;
import unpsjb.labprog.backend.model.EsquemaTurno;
import unpsjb.labprog.backend.model.Horario;
import unpsjb.labprog.backend.model.ItemGeneracionEsquemaTurno;

@Component
public class EsquemaTurnoGenerator {
  private final int BLOQUE_MINIMO_MINUTOS = 120;

  public double calcularDisponibilidad(
      Duration tiempoObjetivo, Collection<ItemGeneracionEsquemaTurno> someItems) {
    Duration tiempoCumplido = Duration.ofMinutes(0);
    for (ItemGeneracionEsquemaTurno item : someItems) {
      tiempoCumplido =
          tiempoCumplido.plusMinutes(
              Duration.between(item.getHoraInicio(), item.getHoraFin()).toMinutes());
    }
    return (double) tiempoCumplido.toMinutes() / tiempoObjetivo.toMinutes();
  }

  public double calcularPotencialCumplimiento(
      DatosEspecialidad datosEspecialidad, Collection<ItemGeneracionEsquemaTurno> someItems) {
    return (calcularDisponibilidad(datosEspecialidad.getTiempoObjetivo(), someItems)
            + datosEspecialidad.getFraccionCumplimiento())
        / 2;
  }

  public void eliminarItem(
      ItemGeneracionEsquemaTurno item,
      Map<Integer, Collection<ItemGeneracionEsquemaTurno>> itemsPorEspecialidad) {
    int especialidadId = item.getStaffMedico().getMedico().getEspecialidad().getId();
    Collection<ItemGeneracionEsquemaTurno> itemsEspecialidad =
        itemsPorEspecialidad.get(especialidadId);
    if (itemsEspecialidad != null) {
      itemsEspecialidad.remove(item);
      if (itemsEspecialidad.isEmpty()) {
        itemsPorEspecialidad.remove(especialidadId);
      }
    }
  }

  public ItemGeneracionEsquemaTurno elegirItem(
      LocalTime hora,
      DiaSemana diaSemana,
      DatosGeneracionHorariosDTO datosGeneracion,
      Map<Integer, Collection<ItemGeneracionEsquemaTurno>> itemsPorEspecialidad,
      int medicoAtendiendoId) {
    List<DatosEspecialidad> datosEspecialidadesPorPrioridad =
        new ArrayList<>(datosGeneracion.getDatosEspecialidades());
    datosEspecialidadesPorPrioridad.sort(
        Comparator.comparingDouble(
            datosEspecialidad ->
                calcularPotencialCumplimiento(
                    datosEspecialidad,
                    itemsPorEspecialidad.get(datosEspecialidad.getEspecialidad().getId()))));
    for (DatosEspecialidad datosEspecialidadElegida : datosEspecialidadesPorPrioridad) {
      Collection<ItemGeneracionEsquemaTurno> itemsElegidos =
          itemsPorEspecialidad.get(datosEspecialidadElegida.getEspecialidad().getId());
      if (itemsElegidos == null || itemsElegidos.isEmpty()) {
        continue;
      }
      Horario horarioNecesitado = null;
      ItemGeneracionEsquemaTurno itemElegido = null;
      for (ItemGeneracionEsquemaTurno item : itemsElegidos) {
        horarioNecesitado =
            item.getStaffMedico().getMedico().getId() == medicoAtendiendoId
                ? new Horario(hora, hora.plusMinutes(datosEspecialidadElegida.getIntervalo()))
                : new Horario(hora, hora.plusMinutes(BLOQUE_MINIMO_MINUTOS));
        Horario horarioMedico = new Horario(item.getHoraInicio(), item.getHoraFin());
        if (horarioMedico.contiene(horarioNecesitado) && item.getDiaSemana() == diaSemana) {
          itemElegido = item;
          break;
        }
      }
      if (itemElegido != null) {
        return itemElegido;
      }
    }
    return null;
  }

  public Collection<EsquemaTurno> generate(
      Map<Integer, Collection<ItemGeneracionEsquemaTurno>> itemsPorEspecialidad,
      DatosGeneracionHorariosDTO datosGeneracion) {
    Collection<EsquemaTurno> esquemaTurnos = new ArrayList<>();
    for (DatosConsultorio datosConsultorio : datosGeneracion.getDatosConsultorios()) {
      for (DatosDia datosDia : datosConsultorio.getDatosDias()) {
        DiaSemana diaSemana = datosDia.getDiaSemana();
        if (diaSemana == DiaSemana.DOMINGO || diaSemana == DiaSemana.SABADO) {
          continue;
        }
        int minutosRecorridos = 0;
        int incremento = 5;
        LocalTime horaActual = LocalTime.of(0, 0);
        Horario horario = new Horario(LocalTime.of(0, 0), LocalTime.of(0, 0));
        ItemGeneracionEsquemaTurno itemAnterior = null;
        ItemGeneracionEsquemaTurno itemActual = null;
        int medicoAtendiendoId = -1;
        do {
          itemActual =
              elegirItem(
                  horaActual, diaSemana, datosGeneracion, itemsPorEspecialidad, medicoAtendiendoId);
          if (itemAnterior == null && itemActual == null) {
            incremento = 5;
            medicoAtendiendoId = -1;
          } else if (itemAnterior == null && itemActual != null) {
            if (datosDia.getTiempoObjetivo().minus(datosDia.getTiempoCumplido()).toMinutes()
                < BLOQUE_MINIMO_MINUTOS) {
              break;
            }
            incremento = BLOQUE_MINIMO_MINUTOS;
            horario = new Horario(horaActual, horaActual.plusMinutes(incremento));
            medicoAtendiendoId = itemActual.getStaffMedico().getMedico().getId();
            DatosEspecialidad datosEspecialidad =
                datosGeneracion.getDatosEspecialidadById(
                    itemActual.getStaffMedico().getMedico().getEspecialidad().getId());
            datosEspecialidad.setTiempoCumplido(
                datosEspecialidad.getTiempoCumplido().plusMinutes(incremento));
            datosDia.setTiempoCumplido(datosDia.getTiempoCumplido().plusMinutes(incremento));
            itemActual.setHoraInicio(horaActual.plusMinutes(incremento));
          } else if (itemAnterior != null && itemActual != null && itemAnterior == itemActual) {
            int intervalo =
                datosGeneracion
                    .getDatosEspecialidadById(
                        itemActual.getStaffMedico().getMedico().getEspecialidad().getId())
                    .getIntervalo();
            incremento = intervalo;
            horario.setHoraFin(horaActual.plusMinutes(incremento));
            medicoAtendiendoId = itemActual.getStaffMedico().getMedico().getId();
            DatosEspecialidad datosEspecialidad =
                datosGeneracion.getDatosEspecialidadById(
                    itemActual.getStaffMedico().getMedico().getEspecialidad().getId());
            datosEspecialidad.setTiempoCumplido(
                datosEspecialidad.getTiempoCumplido().plusMinutes(intervalo));
            datosDia.setTiempoCumplido(datosDia.getTiempoCumplido().plusMinutes(incremento));
            itemActual.setHoraInicio(horaActual.plusMinutes(incremento));
          } else if (itemAnterior != null && itemActual != null && itemAnterior != itemActual) {
            DatosEspecialidad datosEspecialidad =
                datosGeneracion.getDatosEspecialidadById(
                    itemAnterior.getStaffMedico().getMedico().getEspecialidad().getId());
            EsquemaTurno esquema = new EsquemaTurno();
            esquema.setDiaSemana(diaSemana);
            esquema.setHoraInicio(horario.getHoraInicio());
            esquema.setHoraFin(horario.getHoraFin());
            esquema.setIntervalo(datosEspecialidad.getIntervalo());
            esquema.setConsultorio(datosConsultorio.getConsultorio());
            esquema.setStaffMedico(itemAnterior.getStaffMedico());
            esquema.setSobreturno(datosEspecialidad.getSobreturno());
            esquema.setNombre("Esquema generado");
            esquema.setDescripcion("Generado automáticamente");
            esquemaTurnos.add(esquema);
            if (datosDia.getTiempoObjetivo().minus(datosDia.getTiempoCumplido()).toMinutes()
                < BLOQUE_MINIMO_MINUTOS) {
              break;
            }
            incremento = BLOQUE_MINIMO_MINUTOS;
            horario = new Horario(horaActual, horaActual.plusMinutes(incremento));
            medicoAtendiendoId = itemActual.getStaffMedico().getMedico().getId();
            datosEspecialidad =
                datosGeneracion.getDatosEspecialidadById(
                    itemActual.getStaffMedico().getMedico().getEspecialidad().getId());
            datosEspecialidad.setTiempoCumplido(
                datosEspecialidad.getTiempoCumplido().plusMinutes(incremento));
            datosDia.setTiempoCumplido(datosDia.getTiempoCumplido().plusMinutes(incremento));
            itemActual.setHoraInicio(horaActual.plusMinutes(incremento));
          } else if (itemAnterior != null && itemActual == null) {
            DatosEspecialidad datosEspecialidad =
                datosGeneracion.getDatosEspecialidadById(
                    itemAnterior.getStaffMedico().getMedico().getEspecialidad().getId());
            EsquemaTurno esquema = new EsquemaTurno();
            esquema.setDiaSemana(diaSemana);
            esquema.setHoraInicio(horario.getHoraInicio());
            esquema.setHoraFin(horario.getHoraFin());
            esquema.setIntervalo(datosEspecialidad.getIntervalo());
            esquema.setConsultorio(datosConsultorio.getConsultorio());
            esquema.setStaffMedico(itemAnterior.getStaffMedico());
            esquema.setSobreturno(datosEspecialidad.getSobreturno());
            esquema.setNombre("Esquema generado");
            esquema.setDescripcion("Generado automáticamente");
            esquemaTurnos.add(esquema);
            incremento = 5;
            medicoAtendiendoId = -1;
          }
          horaActual = horaActual.plusMinutes(incremento);
          minutosRecorridos += incremento;
          itemAnterior = itemActual;
        } while (minutosRecorridos < 24 * 60
            && datosDia.getTiempoObjetivo().compareTo(datosDia.getTiempoCumplido()) > 0);
      }
    }
    return esquemaTurnos;
  }
}
