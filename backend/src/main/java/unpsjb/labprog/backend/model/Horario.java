package unpsjb.labprog.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Duration;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Horario {
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  private LocalTime horaInicio;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  private LocalTime horaFin;
  private Duration duracion;

  public Horario(LocalTime horaInicio, LocalTime horaFin) {
    this.horaInicio = horaInicio;
    this.horaFin = horaFin;
    this.duracion = Duration.between(horaInicio, horaFin);
  }

  public static LocalTime horaMaxima(LocalTime a, LocalTime b) {
    return a.isAfter(b) ? a : b;
  }

  public static LocalTime horaMinima(LocalTime a, LocalTime b) {
    return a.isBefore(b) ? a : b;
  }

  public void setHoraFin(LocalTime horaFin) {
    this.horaFin = horaFin;
    this.duracion = Duration.between(this.horaInicio, this.horaFin);
  }

  public void setHoraInicio(LocalTime horaInicio) {
    this.horaInicio = horaInicio;
    this.duracion = Duration.between(this.horaInicio, this.horaFin);
  }

  public void setDuracion(Duration duration) {
    this.duracion = duration;
  }

  public Horario interseccion(Horario b) {
    Horario horario =
        new Horario(
            horaMaxima(this.getHoraInicio(), b.getHoraInicio()),
            horaMinima(this.getHoraFin(), b.getHoraFin()));
    if (horario.getHoraInicio().isBefore(horario.getHoraFin())) {
      horario.setDuracion(Duration.between(horario.getHoraInicio(), horario.getHoraFin()));
      return horario;
    } else {
      return null;
    }
  }

  public boolean contiene(Horario b) {
    return estaDentro(b.horaInicio) && estaDentro(b.horaFin);
  }

  private boolean estaDentro(LocalTime t) {
    if (horaInicio.isBefore(horaFin) || horaInicio.equals(horaFin)) {
      return !t.isBefore(horaInicio) && !t.isAfter(horaFin);
    } else {
      return !t.isBefore(horaInicio) || !t.isAfter(horaFin);
    }
  }
}
