package unpsjb.labprog.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EsquemaTurno {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private String nombre;
  private String descripcion;

  @Enumerated(EnumType.STRING)
  private DiaSemana diaSemana;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  private LocalTime horaInicio;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  private LocalTime horaFin;
  private Integer intervalo;

  @ManyToOne(optional = false)
  @JoinColumn(nullable = false)
  private Consultorio consultorio;

  @ManyToOne(optional = true)
  @JoinColumn(nullable = true)
  private StaffMedico staffMedico;

  private int sobreturno;
}
