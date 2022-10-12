package br.com.kronos.backend.aplicacao.pessoa;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroTalento {

	private Long idPessoa;
	private int idTipoTalento;
	private Integer qtdTotal;
	private Integer numeroPagina;
	
}