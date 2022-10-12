package br.com.kronos.backend.aplicacao.desempenho;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroDesempenho{

    private Long id;  
    private Long idSubFaseExecucao; 
    private Long idCredito;
	private Integer qtdTotal;
	private Integer numeroPagina;
}