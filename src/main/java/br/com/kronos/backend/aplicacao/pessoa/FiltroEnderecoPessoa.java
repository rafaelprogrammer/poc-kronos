package br.com.kronos.backend.aplicacao.pessoa;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroEnderecoPessoa {

	private Integer id;
	private String cep;
	private Integer idPessoa;
	private Integer qtdTotal;
	private Integer numeroPagina;
}