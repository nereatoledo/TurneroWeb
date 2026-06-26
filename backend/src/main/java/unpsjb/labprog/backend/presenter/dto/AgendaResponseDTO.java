package unpsjb.labprog.backend.presenter.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unpsjb.labprog.backend.model.Consultorio;
import unpsjb.labprog.backend.model.DiaSemana;
import unpsjb.labprog.backend.model.Medico;
import unpsjb.labprog.backend.model.Point;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgendaResponseDTO {
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
  private LocalDate fecha;
  private DiaSemana diaSemana;
  private List<EsquemaTurnoAgenda> agendaDetalles;
  private boolean esFeriado;

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class EsquemaTurnoAgenda {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaFin;
    private Medico medico;
    private CentroAtencionInfo centroAtencion;
    private Consultorio consultorio;
    private Integer intervalo;
    private List<SlotDTO> slots;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CentroAtencionInfo {
    private String nombre;
    private String direccion;
    private String ciudad;
    private String provincia;
    private String telefono;
    private Point coordenadas;
  }
}
