package br.com.kronos.backend.aplicacao.instituicao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroInstituicao {

	private Long id;
	private String nome;
	private Boolean ativo;
	private Long idEmpresa;
	private Boolean mantenedora;
	private Integer qtdTotal;
	private Integer numeroPagina;
}

