package unpsjb.labprog.backend.model;

import java.time.DayOfWeek;

public enum DiaSemana {
    LUNES,
    MARTES,
    MIERCOLES,
    JUEVES,
    VIERNES,
    SABADO,
    DOMINGO;

    public static DiaSemana from(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY: return LUNES;
            case TUESDAY: return MARTES;
            case WEDNESDAY: return MIERCOLES;
            case THURSDAY: return JUEVES;
            case FRIDAY: return VIERNES;
            case SATURDAY: return SABADO;
            case SUNDAY: return DOMINGO;
            default: throw new IllegalArgumentException("Día no válido: " + dayOfWeek);
        }
    }
}
