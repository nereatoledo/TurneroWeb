package unpsjb.labprog.backend.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unpsjb.labprog.backend.exceptions.AtributoInvalidoException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DatosGeneracionHorariosDTO {
  private int centroAtencionId;
  private Collection<DatosConsultorio> datosConsultorios;
  private Collection<DatosEspecialidad> datosEspecialidades;

  public Duration getTiempoTodosConsultorios() {
    Duration total = Duration.ofHours(0);
    for (DatosConsultorio atencionConsultorio : datosConsultorios) {
      for (DatosDia horasPorDia : atencionConsultorio.getDatosDias()) {
        total = total.plusHours(horasPorDia.getHorasAtencion());
      }
    }
    return total;
  }

  public void distribuirTiempoEspecialidades() {
    List<DatosEspecialidad> datosEspecialidades = new ArrayList<>(getDatosEspecialidades());
    int totalMin = (int) getTiempoTodosConsultorios().toMinutes();
    int granulo = 5;
    double sumaDistribucion =
        datosEspecialidades.stream().mapToDouble(DatosEspecialidad::getDistribucion).sum();
    if (Math.abs(sumaDistribucion - 100.0) > 0.01) {
      throw new AtributoInvalidoException("La suma de los porcentajes no es 100%");
    }
    List<Double> minutosExactos =
        datosEspecialidades.stream().map(d -> (d.getDistribucion() / 100.0) * totalMin).toList();
    List<Integer> minutosRedondeados = new ArrayList<>();
    List<Double> errores = new ArrayList<>();
    for (double exacto : minutosExactos) {
      int redondeado = Math.round((float) exacto / granulo) * granulo;
      minutosRedondeados.add(redondeado);
      errores.add(exacto - redondeado);
    }
    int suma = minutosRedondeados.stream().mapToInt(Integer::intValue).sum();
    int diferencia = totalMin - suma;
    while (diferencia != 0) {
      int signo = Integer.signum(diferencia);
      int ajuste = granulo * signo;
      int mejorIdx = -1;
      double maxError = (signo < 0) ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
      for (int i = 0; i < errores.size(); i++) {
        double error = errores.get(i);
        boolean mejora = (signo < 0 && error > maxError) || (signo > 0 && error < maxError);
        if (mejora) {
          maxError = error;
          mejorIdx = i;
        }
      }
      if (mejorIdx == -1) {
        throw new IllegalStateException(
            "No se encontró índice para ajustar, esto no debería pasar.");
      }
      minutosRedondeados.set(mejorIdx, minutosRedondeados.get(mejorIdx) + ajuste);
      errores.set(mejorIdx, errores.get(mejorIdx) - ajuste);
      diferencia -= ajuste;
    }
    for (int i = 0; i < datosEspecialidades.size(); i++) {
      datosEspecialidades.get(i).setTiempoObjetivo(Duration.ofMinutes(minutosRedondeados.get(i)));
      datosEspecialidades.get(i).setTiempoCumplido(Duration.ofMinutes(0));
    }
  }

  public void cargarTiemposConsultorio() {
    for (DatosConsultorio datosConsultorio : datosConsultorios) {
      for (DatosDia datosDia : datosConsultorio.getDatosDias()) {
        datosDia.setTiempoCumplido(Duration.ofMinutes(0));
        datosDia.setTiempoObjetivo(Duration.ofHours(datosDia.getHorasAtencion()));
      }
    }
  }

  public void verificarDistribuciones() {
    double suma = 0;
    for (DatosEspecialidad datosEspecialidad : datosEspecialidades) {
      if (datosEspecialidad.getDistribucion() < 0) {
        throw new AtributoInvalidoException("La distribución no puede ser negativa");
      }
      suma += datosEspecialidad.getDistribucion();
    }
    if (suma != 100) {
      throw new AtributoInvalidoException("La suma de las distribuciones debe ser 100%");
    }
  }

  private void verificarIntervalos() {
    for (DatosEspecialidad datosEspecialidad : datosEspecialidades) {
      if (datosEspecialidad.getIntervalo() <= 0)
        throw new AtributoInvalidoException(
            "La duración de turnos no puede ser menor o igual a 0 minutos");
      if (datosEspecialidad.getIntervalo() % 5 != 0)
        throw new AtributoInvalidoException(
            "La duración de turnos solo puede ir en pasos de 5 minutos");
    }
  }

  public void calcularValores() {
    verificarDistribuciones();
    verificarIntervalos();
    distribuirTiempoEspecialidades();
    cargarTiemposConsultorio();
  }

  public void limpiarEspecialidadesSinObjetivo() {
    Iterator<DatosEspecialidad> it = datosEspecialidades.iterator();
    while (it.hasNext()) {
      DatosEspecialidad datosEspecialidad = it.next();
      if (datosEspecialidad.getDistribucion() <= 0) {
        it.remove();
      }
    }
  }

  public void limpiarConsultoriosSinObjetivo() {
    Iterator<DatosConsultorio> it = datosConsultorios.iterator();
    while (it.hasNext()) {
      DatosConsultorio datosConsultorio = it.next();
      int horasAtencion = 0;
      for (DatosDia datosDia : datosConsultorio.getDatosDias())
        horasAtencion += datosDia.getHorasAtencion();
      if (horasAtencion <= 0) {
        it.remove();
      }
    }
  }

  public void limpiarDiasSinAtencion() {
    for (DatosConsultorio datosConsultorio : datosConsultorios) {
      Iterator<DatosDia> it = datosConsultorio.getDatosDias().iterator();
      while (it.hasNext()) {
        DatosDia datosDia = it.next();
        if (datosDia.getHorasAtencion() <= 0) {
          it.remove();
        }
      }
    }
  }

  public DatosEspecialidad getDatosEspecialidadById(int id) {
    for (DatosEspecialidad datosEspecialidad : datosEspecialidades) {
      if (datosEspecialidad.getEspecialidad().getId() == id) return datosEspecialidad;
    }
    return null;
  }

  public Collection<Especialidad> getEspecialidades() {
    Collection<Especialidad> especialidades = new ArrayList<>();
    for (DatosEspecialidad datosEspecialidad : datosEspecialidades) {
      especialidades.add(datosEspecialidad.getEspecialidad());
    }
    return especialidades;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DatosConsultorio {
    private Consultorio consultorio;
    private Collection<DatosDia> datosDias;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DatosDia {
    private DiaSemana diaSemana;
    private int horasAtencion;
    private Duration tiempoObjetivo;
    private Duration tiempoCumplido;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DatosEspecialidad {
    private Especialidad especialidad;
    private double distribucion;
    private int intervalo;
    private int sobreturno;
    private Duration tiempoObjetivo;
    private Duration tiempoCumplido;

    public double getFraccionCumplimiento() {
      return (double) tiempoCumplido.toMinutes() / tiempoObjetivo.toMinutes();
    }
  }
}
