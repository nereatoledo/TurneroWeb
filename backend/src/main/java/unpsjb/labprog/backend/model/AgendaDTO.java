package unpsjb.labprog.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AgendaDTO {
  private Especialidad especialidad;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
  private LocalDate diaInicio;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
  private LocalDate diaFin;
  private Collection<Dia> dias = new ArrayList<>();
  private String observaciones;

  public void addDia(Dia dia) {
    dias.add(dia);
  }

  public boolean isEmpty() {
    return dias.isEmpty();
  }

  @Setter
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public class Dia {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fecha;
    private DiaSemana diaSemana;
    private Collection<Slot> slots;
  }

  @Setter
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public class Slot {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaFin;
    private String centroAtencionNombre;
    private Consultorio consultorio;
    private Medico medico;
    private EstadoSlot estado;
  }

  public enum EstadoSlot {
    LIBRE,
    OCUPADO
  }

  @Setter
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public class ItemGeneracionAgenda {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fecha;
    private DiaSemana diaSemana;
    private Consultorio consultorio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaFin;
    private Integer intervalo;
    private CentroAtencion centroAtencion;
    private Medico medico;
    private Collection<Turno> turnos;
  }
}
