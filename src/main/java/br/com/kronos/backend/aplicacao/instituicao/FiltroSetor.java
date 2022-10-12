package br.com.kronos.backend.aplicacao.instituicao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroSetor {

	private Long id;
	private String nome;
	private Long idInstituicao;
	private Integer qtdTotal;
	private Integer numeroPagina;
}
