package unpsjb.labprog.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModificacionTurno {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
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
