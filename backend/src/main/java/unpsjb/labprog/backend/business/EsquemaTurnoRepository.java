package unpsjb.labprog.backend.business;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unpsjb.labprog.backend.model.DiaSemana;
import unpsjb.labprog.backend.model.EsquemaTurno;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface EsquemaTurnoRepository
                extends CrudRepository<EsquemaTurno, Integer>, PagingAndSortingRepository<EsquemaTurno, Integer> {

        @Query("SELECT e FROM EsquemaTurno e WHERE e.staffMedico.centro.id = ?1 AND e.diaSemana = ?2")
        List<EsquemaTurno> findByCentroYDia(int idCentro, DiaSemana diaSemana);

        @Query("SELECT e FROM EsquemaTurno e WHERE e.consultorio.id = ?1")
        List<EsquemaTurno> findByConsultorioId(int idConsultorio);

        @Query("SELECT COUNT(e) > 0 FROM EsquemaTurno e " +
                        "WHERE e.consultorio.id = :idConsultorio " +
                        "AND e.diaSemana = :diaSemana " +
                        "AND e.horaInicio < :horaFin " +
                        "AND e.horaFin > :horaInicio")
        boolean existeConflictoEnConsultorio(
                        @Param("idConsultorio") int idConsultorio,
                        @Param("diaSemana") DiaSemana diaSemana,
                        @Param("horaInicio") LocalTime horaInicio,
                        @Param("horaFin") LocalTime horaFin);

        @Query("SELECT COUNT(e) > 0 FROM EsquemaTurno e " +
                        "WHERE e.staffMedico.id = :idStaffMedico " +
                        "AND e.diaSemana = :diaSemana " +
                        "AND e.horaInicio < :horaFin " +
                        "AND e.horaFin > :horaInicio")
        boolean existeConflictoParaMedico(
                        @Param("idStaffMedico") int idStaffMedico,
                        @Param("diaSemana") DiaSemana diaSemana,
                        @Param("horaInicio") LocalTime horaInicio,
                        @Param("horaFin") LocalTime horaFin);

        @Query("SELECT e FROM EsquemaTurno e " +
                        "WHERE e.diaSemana = :dia " +
                        "AND (:idEspecialidad IS NULL OR e.staffMedico.medico.especialidad.id = :idEspecialidad) " +
                        "AND (:idMedico IS NULL OR e.staffMedico.medico.id = :idMedico) " +
                        "AND (:idCentro IS NULL OR e.staffMedico.centro.id = :idCentro)")

        List<EsquemaTurno> buscarParaAgenda(
                        @Param("dia") DiaSemana dia,
                        @Param("idEspecialidad") Integer idEspecialidad,
                        @Param("idMedico") Integer idMedico,
                        @Param("idCentro") Integer idCentro);
}