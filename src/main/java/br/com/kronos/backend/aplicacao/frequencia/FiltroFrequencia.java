package br.com.kronos.backend.aplicacao.frequencia;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroFrequencia{

    private Long id;  
    private LocalDate dataReposicao; 
    private Long idAtividade;
    private Long idSubFaseExecucao;
    private Long idDisciplina;
    private Long idCredito;
    private int anoTurma;
	private Integer qtdTotal;
	private Integer numeroPagina;
}