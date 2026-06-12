package unpsjb.labprog.backend.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore; // <-- AGREGAR IMPORT
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Consultorio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Integer numero;

    @ManyToOne
    @JoinColumn(name = "centro_atencion_id") 
    @JsonIgnore
    private CentroAtencion centro;

}