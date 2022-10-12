package br.com.kronos.backend.aplicacao.pessoa;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroFiliacao {

	private Integer id;
	private Long idPessoaPais;
    private Long idPessoaFilho;
    private Long idTipoFiliacao;
	private Integer qtdTotal;
	private Integer numeroPagina;
}