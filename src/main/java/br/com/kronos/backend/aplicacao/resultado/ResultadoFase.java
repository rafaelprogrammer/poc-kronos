
package br.com.kronos.backend.aplicacao.resultado;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResultadoFase {
    
    private Long id;
    private Double notaFinalNormal; 
    private Double notaFinalRecuperacao; 
    private Integer totalAusencia; 
    private Double percentualAusencia; 
    private Long idFaseExecucao;
    private Long idCredito;
    private Long idMencao;
    private Double notaFinal;
    
	@Builder
    public ResultadoFase(Long id, Double notaFinalNormal, Double notaFinalRecuperacao, Integer totalAusencia, Double percentualAusencia, 
                         Long idFaseExecucao, Long idCredito, Long idMencao, Double notaFinal   ) {

		super();
        this.id = id;
        this.notaFinalNormal = notaFinalNormal; 
        this.notaFinalRecuperacao = notaFinalRecuperacao; 
        this.totalAusencia = exigirNaoNulo(totalAusencia, "Tempo total de ausência"); 
        this.percentualAusencia = exigirNaoNulo(percentualAusencia, "Percentual de ausência"); 
        this.idFaseExecucao = exigirNaoNulo(idFaseExecucao, "Fase (semestre) de execução"); 
        this.idCredito = exigirNaoNulo(idCredito, "Crédito (Aluno)"); 
        this.idMencao = idMencao; 
        this.notaFinal = notaFinal; 
	}
}