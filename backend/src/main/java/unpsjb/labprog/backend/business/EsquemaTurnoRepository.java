package unpsjb.labprog.backend.business;

import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unpsjb.labprog.backend.model.DiaSemana;
import unpsjb.labprog.backend.model.EsquemaTurno;

@Repository
public interface EsquemaTurnoRepository
    extends CrudRepository<EsquemaTurno, Integer>,
        PagingAndSortingRepository<EsquemaTurno, Integer> {
  @Query("SELECT e FROM EsquemaTurno e WHERE e.staffMedico.centro.id = ?1 AND e.diaSemana = ?2")
  List<EsquemaTurno> findByCentroYDia(int idCentro, DiaSemana diaSemana);

  @Query("SELECT e FROM EsquemaTurno e WHERE e.consultorio.id = ?1")
  List<EsquemaTurno> findByConsultorioId(int idConsultorio);

  @Query(
      "SELECT COUNT(e) > 0 FROM EsquemaTurno e "
          + "WHERE e.consultorio.id = :idConsultorio "
          + "AND e.diaSemana = :diaSemana "
          + "AND e.horaInicio < :horaFin "
          + "AND e.horaFin > :horaInicio")
  boolean existeConflictoEnConsultorio(
      @Param("idConsultorio") int idConsultorio,
      @Param("diaSemana") DiaSemana diaSemana,
      @Param("horaInicio") LocalTime horaInicio,
      @Param("horaFin") LocalTime horaFin);

  @Query(
      "SELECT COUNT(e) > 0 FROM EsquemaTurno e "
          + "WHERE e.staffMedico.id = :idStaffMedico "
          + "AND e.diaSemana = :diaSemana "
          + "AND e.horaInicio < :horaFin "
          + "AND e.horaFin > :horaInicio")
  boolean existeConflictoParaMedico(
      @Param("idStaffMedico") int idStaffMedico,
      @Param("diaSemana") DiaSemana diaSemana,
      @Param("horaInicio") LocalTime horaInicio,
      @Param("horaFin") LocalTime horaFin);

  @Query(
      "SELECT e FROM EsquemaTurno e "
          + "LEFT JOIN e.staffMedico sm "
          + "WHERE e.diaSemana = :dia "
          + "AND sm IS NOT NULL "
          + "AND (:idEspecialidad IS NULL OR sm.medico.especialidad.id = :idEspecialidad) "
          + "AND (:idMedico IS NULL OR sm.medico.id = :idMedico) "
          + "AND (:idCentro IS NULL OR sm.centro.id = :idCentro) "
          + "AND (:idMedicoExcluido IS NULL OR sm.medico.id <> :idMedicoExcluido) "
          + "AND (:idCentroExcluido IS NULL OR sm.centro.id <> :idCentroExcluido)")
  List<EsquemaTurno> buscarParaAgenda(
      @Param("dia") DiaSemana dia,
      @Param("idEspecialidad") Integer idEspecialidad,
      @Param("idMedico") Integer idMedico,
      @Param("idCentro") Integer idCentro,
      @Param("idMedicoExcluido") Integer idMedicoExcluido,
      @Param("idCentroExcluido") Integer idCentroExcluido);

  @Query(
      "SELECT COUNT(e) > 0 FROM EsquemaTurno e "
          + "LEFT JOIN e.staffMedico sm "
          + "WHERE sm IS NOT NULL "
          + "AND e.diaSemana IN :diasSemana "
          + "AND (:idEspecialidad IS NULL OR sm.medico.especialidad.id = :idEspecialidad) "
          + "AND (:idMedico IS NULL OR sm.medico.id = :idMedico) "
          + "AND (:idCentro IS NULL OR sm.centro.id = :idCentro) "
          + "AND (:idMedicoExcluido IS NULL OR sm.medico.id <> :idMedicoExcluido) "
          + "AND (:idCentroExcluido IS NULL OR sm.centro.id <> :idCentroExcluido)")
  boolean existeEsquemaParaDias(
      @Param("diasSemana") List<DiaSemana> diasSemana,
      @Param("idEspecialidad") Integer idEspecialidad,
      @Param("idMedico") Integer idMedico,
      @Param("idCentro") Integer idCentro,
      @Param("idMedicoExcluido") Integer idMedicoExcluido,
      @Param("idCentroExcluido") Integer idCentroExcluido);

  @Query(
      "SELECT e FROM EsquemaTurno e WHERE e.consultorio.id = :consultorioId AND e.diaSemana = :dia")
  List<EsquemaTurno> findByConsultorioIdYDia(
      @Param("consultorioId") int consultorioId, @Param("dia") DiaSemana dia);

  @Query(
      """
          SELECT esq
          FROM CentroAtencion c
          JOIN c.esquemaTurnos esq
          WHERE esq.staffMedico.medico.especialidad.id = :especialidad_id
              AND (:medico_id IS NULL OR esq.staffMedico.medico.id = :medico_id)
              AND (:centro_id IS NULL OR c.id = :centro_id)
      """)
  List<EsquemaTurno> search(
      @Param("especialidad_id") Integer aEspecialidadId,
      @Param("medico_id") Integer aMedicoId,
      @Param("centro_id") Integer aCentroAtencionId);
}
