package br.com.kronos.backend.aplicacao.pessoa;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroResponsavel {

	private Long id;
    private Long idPessoa;
    private Long idPessoaResponsavel;
    private Long idMatricula;
    private Integer idTipoPessoaResponsavel;
	private Integer qtdTotal;
	private Integer numeroPagina;
}