
package br.com.kronos.backend.aplicacao.resultado;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResultadoSubFase {
    
    private Long id;
    private Double notaFinalNormal; 
    private Double notaFinalRecuperacao; 
    private Integer totalAusencia; 
    private Double percentualAusencia; 
    private Long idSubFaseExecucao;
    private Long idCredito;
    private Long idMencao;
    private Double notaFinal;
    
	@Builder
    public ResultadoSubFase(Long id, Double notaFinalNormal, Double notaFinalRecuperacao, Integer totalAusencia, Double percentualAusencia, 
                         Long idSubFaseExecucao, Long idCredito, Long idMencao, Double notaFinal   ) {

		super();
        this.id = id;
        this.notaFinalNormal = notaFinalNormal; 
        this.notaFinalRecuperacao = notaFinalRecuperacao; 
        this.totalAusencia = exigirNaoNulo(totalAusencia, "Tempo total de ausência"); 
        this.percentualAusencia = exigirNaoNulo(percentualAusencia, "Percentual de ausência"); 
        this.idSubFaseExecucao = exigirNaoNulo(idSubFaseExecucao, "SubFase (bimestre) de execução"); 
        this.idCredito = exigirNaoNulo(idCredito, "Crédito (aluno)"); 
        this.idMencao = idMencao; 
        this.notaFinal = notaFinal; 
	}
}

