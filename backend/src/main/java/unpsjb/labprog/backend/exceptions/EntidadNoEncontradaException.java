package unpsjb.labprog.backend.exceptions;

public class EntidadNoEncontradaException extends RuntimeException {
  public EntidadNoEncontradaException() {}

  public EntidadNoEncontradaException(String mensaje) {
    super(mensaje);
  }
}
