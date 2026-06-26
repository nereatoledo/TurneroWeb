package unpsjb.labprog.backend.presenter.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgendaBusquedaResultadoDTO {
  private boolean esSugerencia;
  private String mensaje;
  private List<AgendaResponseDTO> agendas;
}
