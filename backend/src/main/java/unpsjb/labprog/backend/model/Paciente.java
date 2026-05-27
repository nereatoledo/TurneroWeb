package unpsjb.labprog.backend.model;

import java.time.LocalDate;

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

    private LocalDate fechaNacimiento;

    @ManyToOne
    private ObraSocial obraSocial;
}
