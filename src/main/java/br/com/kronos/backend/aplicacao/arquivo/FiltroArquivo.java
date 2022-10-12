package br.com.kronos.backend.aplicacao.arquivo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FiltroArquivo {

	private Integer id;
	private String nome;
	private Long idPessoa;
	private Integer idTipoArquivo;
	private Integer idTipoConteudo;
	private Integer qtdTotal;
	private Integer numeroPagina;
}
