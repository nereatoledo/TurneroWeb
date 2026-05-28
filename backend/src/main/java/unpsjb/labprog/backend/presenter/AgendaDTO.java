package unpsjb.labprog.backend.presenter;

import java.time.LocalDate;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AgendaDTO {
    private String nombre;
    private String descripcion;
    
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaInicio;
    
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaFin;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio; 
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;    
    
    private Integer idConsultorio; 
    private Integer idMedico; 
}