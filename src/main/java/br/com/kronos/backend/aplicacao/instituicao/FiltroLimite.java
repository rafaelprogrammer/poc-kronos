package br.com.kronos.backend.aplicacao.instituicao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroLimite {

	private Long id;  
    private Long idEscala;
	private Integer qtdTotal;
	private Integer numeroPagina;
}