package unpsjb.labprog.backend.model;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Paciente extends Persona{

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaNacimiento;

    @ManyToOne
    private ObraSocial obraSocial;

    @Column(unique = true, nullable = false)
    private String username; 
}
