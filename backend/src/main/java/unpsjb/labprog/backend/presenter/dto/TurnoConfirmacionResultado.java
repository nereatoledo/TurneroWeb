package unpsjb.labprog.backend.presenter.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unpsjb.labprog.backend.model.Turno;

@Getter
@Setter
@NoArgsConstructor
public class TurnoConfirmacionResultado {

    private Turno turno;
    private boolean requiereConfirmacion;
    private String advertencia;

    public static TurnoConfirmacionResultado ok(Turno turno) {
        TurnoConfirmacionResultado r = new TurnoConfirmacionResultado();
        r.turno = turno;
        r.requiereConfirmacion = false;
        return r;
    }

    public static TurnoConfirmacionResultado conAdvertencia(String mensaje) {
        TurnoConfirmacionResultado r = new TurnoConfirmacionResultado();
        r.requiereConfirmacion = true;
        r.advertencia = mensaje;
        return r;
    }
}
