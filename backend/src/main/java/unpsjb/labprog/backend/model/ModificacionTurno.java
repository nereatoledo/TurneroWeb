package unpsjb.labprog.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModificacionTurno {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
  private LocalDateTime fechaModificacion;

  @Enumerated(EnumType.STRING)
  private EstadoTurno estadoAnterior;

  @Enumerated(EnumType.STRING)
  private EstadoTurno estadoNuevo;

  private String motivo;

  @ManyToOne
  @JoinColumn(name = "turno_id")
  private Turno turno;
}
