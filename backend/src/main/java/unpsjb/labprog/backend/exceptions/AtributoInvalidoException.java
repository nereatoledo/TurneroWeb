package unpsjb.labprog.backend.exceptions;

public class AtributoInvalidoException extends RuntimeException {
  public AtributoInvalidoException() {}

  public AtributoInvalidoException(String mensaje) {
    super(mensaje);
  }
}
