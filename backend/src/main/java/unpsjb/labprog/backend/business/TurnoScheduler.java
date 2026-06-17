package unpsjb.labprog.backend.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler que libera automáticamente los turnos en estado RESERVADO
 * cuyo tiempo de reserva superó los 15 minutos sin que el paciente confirmara.
 *
 * Se ejecuta cada 60 segundos. Si el sistema está bajo carga, los turnos
 * pueden quedar bloqueados hasta 1 minuto extra, lo cual es aceptable.
 */
@Component
public class TurnoScheduler {

    @Autowired
    private TurnoService turnoService;

    @Scheduled(fixedDelay = 60_000)
    public void liberarReservasVencidas() {
        turnoService.liberarReservasVencidas();
    }
}
