package unpsjb.labprog.backend.business;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import unpsjb.labprog.backend.model.AgendaDTO;
import unpsjb.labprog.backend.model.AgendaDTO.Dia;
import unpsjb.labprog.backend.model.AgendaDTO.EstadoSlot;
import unpsjb.labprog.backend.model.AgendaDTO.ItemGeneracionAgenda;
import unpsjb.labprog.backend.model.AgendaDTO.Slot;
import unpsjb.labprog.backend.model.DiaSemana;
import unpsjb.labprog.backend.model.Turno;

@Component
public class AgendaGenerator {

  public List<LocalDate> fechasEntre(LocalDate diaInicio, LocalDate diaFin) {
    List<LocalDate> fechas = new ArrayList<>();
    LocalDate hoy = diaInicio;

    while (!hoy.isAfter(diaFin)) {
      fechas.add(hoy);
      hoy = hoy.plusDays(1);
    }
    return fechas;
  }

  public List<Slot> generarSlotsDeItem(ItemGeneracionAgenda aItemGeneracionAgenda) {
    List<Slot> slots = new ArrayList<>();

    long minutoFin =
        Duration.between(aItemGeneracionAgenda.getHoraInicio(), aItemGeneracionAgenda.getHoraFin())
            .toMinutes();

    LocalTime horaInicioSlot = LocalTime.parse(aItemGeneracionAgenda.getHoraInicio().toString());

    for (int minutoSlot = 0;
        minutoSlot < minutoFin;
        minutoSlot += aItemGeneracionAgenda.getIntervalo()) {
      EstadoSlot estadoSlot = EstadoSlot.LIBRE;

      for (Turno turno : aItemGeneracionAgenda.getTurnos()) {

        long minutoTurno =
            Duration.between(aItemGeneracionAgenda.getHoraInicio(), turno.getHoraInicio())
                .toMinutes();

        if (minutoSlot == minutoTurno) {
          estadoSlot = EstadoSlot.OCUPADO;
          break;
        }
      }

      slots.add(
          new AgendaDTO()
          .new Slot(
              horaInicioSlot,
              horaInicioSlot.plusMinutes(aItemGeneracionAgenda.getIntervalo()),
              aItemGeneracionAgenda.getCentroAtencion().getNombre(),
              aItemGeneracionAgenda.getConsultorio(),
              aItemGeneracionAgenda.getMedico(),
              estadoSlot));

      horaInicioSlot = horaInicioSlot.plusMinutes(aItemGeneracionAgenda.getIntervalo());
    }

    return slots;
  }

  public Dia generarDiaAgenda(
      LocalDate hoy, Collection<ItemGeneracionAgenda> someItemGeneracionAgendas) {

    if (someItemGeneracionAgendas == null) return null;

    List<Slot> slots = new ArrayList<>();
    for (ItemGeneracionAgenda aItemGeneracionAgenda : someItemGeneracionAgendas) {
      slots.addAll(generarSlotsDeItem(aItemGeneracionAgenda));
    }

    if (!slots.isEmpty()) {
      return new AgendaDTO().new Dia(hoy, DiaSemana.from(hoy.getDayOfWeek()), slots);
    }

    return null;
  }

  public AgendaDTO generate(
      LocalDate fechaInicio,
      LocalDate fechaFin,
      Map<LocalDate, Collection<ItemGeneracionAgenda>> items) {

    AgendaDTO agendaDefinitiva = new AgendaDTO();
    for (LocalDate hoy : fechasEntre(fechaInicio, fechaFin)) {

      Dia diaAgenda = generarDiaAgenda(hoy, items.get(hoy));
      if (diaAgenda != null) agendaDefinitiva.addDia(diaAgenda);
    }

    agendaDefinitiva.setDiaInicio(fechaInicio);
    agendaDefinitiva.setDiaFin(fechaFin);
    return agendaDefinitiva;
  }
}
