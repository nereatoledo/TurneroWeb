package unpsjb.labprog.backend.business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unpsjb.labprog.backend.model.EstadoTurno;
import unpsjb.labprog.backend.model.Paciente;
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
           "WHERE t.estado IN :estadosDisponibles " +
           "AND (:especialidadId IS NULL OR t.medico.especialidad.id = :especialidadId) " +
           "AND (:medicoId IS NULL OR t.medico.id = :medicoId) " +
           "AND (:centroId IS NULL OR t.consultorio.centro.id = :centroId) " +
           "AND (t.fecha > :hoy OR (t.fecha = :hoy AND t.horaInicio > :ahora)) " +
           "ORDER BY t.fecha ASC, t.horaInicio ASC")
    List<Turno> buscarDisponiblesParaPaciente(
        @Param("estadosDisponibles") List<EstadoTurno> estadosDisponibles,
        @Param("especialidadId") Integer especialidadId,
        @Param("medicoId") Integer medicoId,
        @Param("centroId") Integer centroId,
        @Param("hoy") LocalDate hoy,
        @Param("ahora") LocalTime ahora
    );

    @Query("SELECT t FROM Turno t WHERE t.fecha = :fecha AND t.horaInicio = :horaInicio " +
           "AND (t.consultorio.id = :consultorioId OR t.medico.id = :medicoId) " +
           "AND t.estado IN :estados")
    List<Turno> buscarConflictos(
        @Param("fecha") LocalDate fecha,
        @Param("horaInicio") LocalTime horaInicio,
        @Param("consultorioId") int consultorioId,
        @Param("medicoId") int medicoId,
        @Param("estados") List<EstadoTurno> estados
    );

    @Modifying
    @Query("UPDATE Turno t SET t.paciente = :paciente, t.estado = :estadoNuevo " +
           "WHERE t.id = :id AND t.estado IN :estadosDisponibles")
    int confirmar(
        @Param("id") int id,
        @Param("paciente") Paciente paciente,
        @Param("estadoNuevo") EstadoTurno estadoNuevo,
        @Param("estadosDisponibles") List<EstadoTurno> estadosDisponibles
    );

    @Modifying
    @Query("UPDATE Turno t SET t.estado = :estadoNuevo, t.paciente = :paciente, t.timestamp = :ts " +
           "WHERE t.id = :id AND t.estado IN :estadosDisponibles")
    int reservar(
        @Param("id") int id,
        @Param("paciente") Paciente paciente,
        @Param("estadoNuevo") EstadoTurno estadoNuevo,
        @Param("estadosDisponibles") List<EstadoTurno> estadosDisponibles,
        @Param("ts") LocalDateTime ts
    );

    @Modifying
    @Query("UPDATE Turno t SET t.estado = unpsjb.labprog.backend.model.EstadoTurno.CANCELADO, t.paciente = null, t.timestamp = null " +
           "WHERE t.estado = unpsjb.labprog.backend.model.EstadoTurno.RESERVADO AND t.timestamp < :limite")
    int liberarReservasVencidas(@Param("limite") LocalDateTime limite);

    @Modifying
    @Query("UPDATE Turno t SET t.estado = unpsjb.labprog.backend.model.EstadoTurno.PROGRAMADO, t.paciente = null, t.timestamp = null " +
           "WHERE t.id = :id AND t.estado = unpsjb.labprog.backend.model.EstadoTurno.RESERVADO AND t.paciente.id = :pacienteId")
    int cancelarReserva(@Param("id") int id, @Param("pacienteId") int pacienteId);


    @Query("SELECT t FROM Turno t WHERE t.medico.id = :medicoId " +
           "AND t.fecha BETWEEN :desde AND :hasta " +
           "AND t.estado IN :estados " +
           "ORDER BY t.fecha ASC, t.horaInicio ASC")
    List<Turno> buscarParaReprogramar(
        @Param("medicoId") int medicoId,
        @Param("desde") LocalDate desde,
        @Param("hasta") LocalDate hasta,
        @Param("estados") List<EstadoTurno> estados
    );

    @Query("SELECT COUNT(t) > 0 FROM Turno t " +
           "WHERE t.paciente.id = :pacienteId " +
           "AND t.medico.id = :medicoId " +
           "AND t.fecha = :fecha " +
           "AND t.estado IN :estados")
    boolean existeTurnoMismoMedicoMisDia(
        @Param("pacienteId") int pacienteId,
        @Param("medicoId") int medicoId,
        @Param("fecha") LocalDate fecha,
        @Param("estados") List<EstadoTurno> estados
    );

    @Query("SELECT t FROM Turno t " +
           "WHERE t.paciente.id = :pacienteId " +
           "AND t.medico.especialidad.id = :especialidadId " +
           "AND t.fecha = :fecha " +
           "AND t.estado IN :estados")
    List<Turno> buscarTurnosMismaEspecialidadMisDia(
        @Param("pacienteId") int pacienteId,
        @Param("especialidadId") int especialidadId,
        @Param("fecha") LocalDate fecha,
        @Param("estados") List<EstadoTurno> estados
    );
}