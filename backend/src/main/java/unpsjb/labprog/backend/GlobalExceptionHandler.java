package unpsjb.labprog.backend;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import unpsjb.labprog.backend.exceptions.AtributoDuplicadoException;
import unpsjb.labprog.backend.exceptions.AtributoInvalidoException;
import unpsjb.labprog.backend.exceptions.EntidadNoEncontradaException;
import unpsjb.labprog.backend.exceptions.HorarioIncompatibleException;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException aException) {
    List<String> errores =
        aException.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getDefaultMessage())
            .collect(Collectors.toList());
    return Response.error409(null, String.join(", ", errores));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Object> handleParseErrors(HttpMessageNotReadableException aException) {
    return Response.error409(null, aException.getMessage());
  }

  @ExceptionHandler(AtributoInvalidoException.class)
  public ResponseEntity<Object> handleAtributoInvalido(AtributoInvalidoException aException) {
    return Response.error409(null, aException.getMessage());
  }

  @ExceptionHandler(AtributoDuplicadoException.class)
  public ResponseEntity<Object> handleAtributoDuplicado(AtributoDuplicadoException aException) {
    return Response.error409(null, aException.getMessage());
  }

  @ExceptionHandler(EntidadNoEncontradaException.class)
  public ResponseEntity<Object> handleEntidadNoEncontrada(EntidadNoEncontradaException aException) {
    return Response.error409(null, aException.getMessage());
  }

  @ExceptionHandler(HorarioIncompatibleException.class)
  public ResponseEntity<Object> handleHorarioIncompatible(HorarioIncompatibleException aException) {
    return Response.error409(null, aException.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> lastHandle(Exception aException) {
    return Response.error409(aException.getStackTrace(), aException.getMessage());
  }
}
