package unpsjb.labprog.backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StaffMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "centro_atencion_id", nullable = false)
    private CentroAtencion centro;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "staff_medico_id") 
    private Collection<DisponibilidadMedico> disponibilidad = new ArrayList<>();

    public void agregarDisponibilidad(DisponibilidadMedico disp) {
        this.disponibilidad.add(disp);
    }

    public void removerDisponibilidad(DisponibilidadMedico disp) {
        this.disponibilidad.remove(disp);
    }
}