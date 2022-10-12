package br.com.kronos.backend.aplicacao.desempenho;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroDesempenhoAvaliacao {

    private Long idDesempenho;
    private Long idAvaliacao;
	private Integer qtdTotal;
	private Integer numeroPagina;
}
