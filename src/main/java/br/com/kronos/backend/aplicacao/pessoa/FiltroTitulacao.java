package br.com.kronos.backend.aplicacao.pessoa;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroTitulacao {

	private Integer id;
    private Long idPessoa;
	private Integer qtdTotal;
	private Integer numeroPagina;
}