package br.com.kronos.backend.aplicacao.avaliacao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroAvaliacaoHabilidade{

    private Long id;  
    private Long idAvaliacao;
    private Long idHabilidade;
	private Integer qtdTotal;
	private Integer numeroPagina;
}