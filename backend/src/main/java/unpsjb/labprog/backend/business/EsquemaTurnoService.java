package unpsjb.labprog.backend.business;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unpsjb.labprog.backend.model.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class EsquemaTurnoService {

    @Autowired
    EsquemaTurnoRepository repository;

    @Transactional
    public List<EsquemaTurno> generarAgendaDinamica(StaffMedico staff) {
        List<EsquemaTurno> plantillasGeneradas = new ArrayList<>();
        CentroAtencion centro = staff.getCentro();

        for (DisponibilidadMedico disp : staff.getDisponibilidad()) {
            DiaSemana dia = DiaSemana.valueOf(disp.getDiaSemana().toUpperCase());

            Consultorio consultorioAsignado = centro.getConsultorios().stream()
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No hay consultorios en el centro."));

            EsquemaTurno regla = new EsquemaTurno();
            regla.setNombre("Plantilla " + dia.name());
            regla.setDescripcion("Turnos regulares asignados los " + dia.name());
            regla.setDiaSemana(dia);
            
            regla.setHoraInicio(disp.getHoraInicio());
            regla.setHoraFin(disp.getHoraFin());
            
            regla.setConsultorio(consultorioAsignado);
            regla.setStaffMedico(staff);

            plantillasGeneradas.add(regla);
        }

        repository.saveAll(plantillasGeneradas);
        return plantillasGeneradas;
    }
}