package unpsjb.labprog.backend.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CentroAtencion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String localidad;

    @Column(nullable = false)    
    private String provincia;
    
    @Column(nullable = false)    
    private String telefono;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "coordenadas_id", nullable = false)
    private Point coordenadas;

    @OneToMany(mappedBy = "centro", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Consultorio> consultorios = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "centro_especialidad",
        joinColumns = @JoinColumn(name = "centro_atencion_id"),
        inverseJoinColumns = @JoinColumn(name = "especialidad_id")
    )
    private Set<Especialidad> especialidades = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "centro_medico",
        joinColumns = @JoinColumn(name = "centro_atencion_id"),
        inverseJoinColumns = @JoinColumn(name = "medico_id")
    )
    @JsonIgnore 
    private Set<Medico> medicos = new HashSet<>();

    public void agregarConsultorio(Consultorio consultorio) {
        consultorio.setCentro(this);
        this.consultorios.add(consultorio);
    }

    public void removerConsultorio(Consultorio consultorio) {
        this.consultorios.remove(consultorio);
        consultorio.setCentro(null);
    }

    public void agregarEspecialidad(Especialidad especialidad) {
        this.especialidades.add(especialidad);
    }

    public void removerEspecialidad(Especialidad especialidad) {
        this.especialidades.removeIf(e -> e.getId() == especialidad.getId());
    }

    public void agregarMedico(Medico medico) {
        this.medicos.add(medico);
    }

    public void removerMedico(Medico medico) {
        this.medicos.removeIf(m -> m.getId() == medico.getId());
    }
}