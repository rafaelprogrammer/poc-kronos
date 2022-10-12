package br.com.kronos.backend.aplicacao.desempenho;

import lombok.Builder;
import lombok.Getter;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

@Getter
public class DesempenhoAvaliacao {

	private Long idDesempenho;
	private Long idAvaliacao;
	
	@Builder
	public DesempenhoAvaliacao(Long idDesempenho, Long idAvaliacao) {
		
		this.idDesempenho = exigirNaoNulo(idDesempenho, "Ficha de desempenho"); 
		this.idAvaliacao = exigirNaoNulo(idAvaliacao, "Avaliação");
	}
	
}

