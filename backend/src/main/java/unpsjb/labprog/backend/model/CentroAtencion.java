package unpsjb.labprog.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "centro_atencion_id") 
    private Collection<Consultorio> consultorios = new ArrayList<>();

    public void agregarConsultorio(Consultorio consultorio) {
        this.consultorios.add(consultorio);
    }

    public void removerConsultorio(Consultorio consultorio) {
        this.consultorios.remove(consultorio);
    }
}