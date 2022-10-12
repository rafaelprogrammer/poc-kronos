package br.com.kronos.backend.aplicacao.instituicao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroSala {

	private Long id;
	private Long idTipoSala;
	private Long idInstituicao;
	private Integer qtdTotal;
	private Integer numeroPagina;
}
