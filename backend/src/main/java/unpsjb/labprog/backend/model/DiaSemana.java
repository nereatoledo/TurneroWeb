package unpsjb.labprog.backend.model;

import java.time.DayOfWeek;

public enum DiaSemana {
    LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO;

    public static DiaSemana desdeJava(DayOfWeek dayOfWeek) {
        return DiaSemana.values()[dayOfWeek.getValue() - 1];
    }
}
