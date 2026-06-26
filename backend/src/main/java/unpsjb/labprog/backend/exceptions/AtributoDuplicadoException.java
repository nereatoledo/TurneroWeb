package unpsjb.labprog.backend.exceptions;

public class AtributoDuplicadoException extends RuntimeException {
  public AtributoDuplicadoException() {}

  public AtributoDuplicadoException(String mensaje) {
    super(mensaje);
  }
}
