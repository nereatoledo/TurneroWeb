package unpsjb.labprog.backend.business;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unpsjb.labprog.backend.model.EstadoTurno;
import unpsjb.labprog.backend.model.Turno;

@Repository
public interface TurnoRepository extends CrudRepository<Turno, Integer>, PagingAndSortingRepository<Turno, Integer> {

    Page<Turno> findByPaciente(int pacienteId, Pageable pageable);

    @Query("SELECT t FROM Turno t WHERE t.estado IN :estados")
    List<Turno> findByEstados(@Param("estados") List<EstadoTurno> estados);

    @Query("SELECT t FROM Turno t WHERE t.fecha = :fecha AND t.consultorio.id = :consultorioId AND t.estado IN :estados")
    List<Turno> find(@Param("fecha") LocalDate fecha, @Param("consultorioId") int consultorioId, @Param("estados") List<EstadoTurno> estados);

    @Query("SELECT t FROM Turno t WHERE t.fecha = :fecha " +
           "AND (:consultorioId IS NULL OR t.consultorio.id = :consultorioId) " +
           "AND (:medicoId IS NULL OR t.medico.id = :medicoId) " +
           "AND (:pacienteId IS NULL OR t.paciente.id = :pacienteId) " +
           "AND (t.estado IN :estados)")
    List<Turno> find(@Param("fecha") LocalDate fecha, @Param("consultorioId") Integer consultorioId, @Param("medicoId") Integer medicoId, @Param("pacienteId") Integer pacienteId, @Param("estados") List<EstadoTurno> estados);

    @Query("SELECT t FROM Turno t WHERE " +
           "(:fecha IS NULL OR t.fecha = :fecha) " +
           "AND (:estado IS NULL OR t.estado = :estado) " +
           "AND (:pacienteId IS NULL OR t.paciente.id = :pacienteId) " +
           "AND (:especialidadId IS NULL OR t.medico.especialidad.id = :especialidadId) " +
           "AND (:medicoId IS NULL OR t.medico.id = :medicoId)")
    Page<Turno> search(@Param("fecha") LocalDate fecha, @Param("estado") EstadoTurno estado, @Param("pacienteId") Integer pacienteId, @Param("especialidadId") Integer especialidadId, @Param("medicoId") Integer medicoId, Pageable pageable);

    @Query("SELECT t FROM Turno t " +
           "WHERE t.estado = :estadoDisponible " +
           "AND (:especialidadId IS NULL OR t.medico.especialidad.id = :especialidadId) " +
           "AND (:medicoId IS NULL OR t.medico.id = :medicoId) " +
           "AND (:centroId IS NULL OR t.consultorio.centro.id = :centroId) " + 
           "ORDER BY t.fecha ASC, t.horaInicio ASC")
    List<Turno> buscarDisponiblesParaPaciente(
        @Param("estadoDisponible") EstadoTurno estadoDisponible,
        @Param("especialidadId") Integer especialidadId,
        @Param("medicoId") Integer medicoId,
        @Param("centroId") Integer centroId
    );
}