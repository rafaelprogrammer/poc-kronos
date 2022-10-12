package br.com.kronos.backend.aplicacao.resultado;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroResultadoFase{

    private Long id;  
    private Long idFaseExecucao;
    private Long idCredito;
	private Integer qtdTotal;
	private Integer numeroPagina;
}