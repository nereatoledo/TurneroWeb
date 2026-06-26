package unpsjb.labprog.backend.exceptions;

public class HorarioIncompatibleException extends RuntimeException {
  public HorarioIncompatibleException() {}

  public HorarioIncompatibleException(String mensaje) {
    super(mensaje);
  }
}
