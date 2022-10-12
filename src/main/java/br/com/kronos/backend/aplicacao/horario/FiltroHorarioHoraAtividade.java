package br.com.kronos.backend.aplicacao.horario;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroHorarioHoraAtividade {

    private Long idHorario;
    private Long idHoraAtividade;
	private Integer qtdTotal;
	private Integer numeroPagina;
}
