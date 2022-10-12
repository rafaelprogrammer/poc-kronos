
package br.com.kronos.backend.aplicacao.desempenho;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Desempenho {
    
    private Long id;
    private Long idSubFaseExecucao; 
    private Long idCredito;
    private LocalDate dataAvaliacao;
    private StringBuffer parecer;
    private LocalDate dataParecer;
    private StringBuffer consideracoes;
    private LocalDate dataConsideracoes;

	@Builder
    public Desempenho(Long id, Long idSubFaseExecucao, Long idCredito, LocalDate dataAvaliacao, StringBuffer parecer, 
                      LocalDate dataParecer, StringBuffer consideracoes, LocalDate dataConsideracoes) {

		super();
        this.id = id;
        this.idSubFaseExecucao = exigirNaoNulo(idSubFaseExecucao, "Sub-fase (bimestre) de execução"); 
        this.idCredito = exigirNaoNulo(idCredito, "Crédito (aluno)"); 
        this.dataAvaliacao = dataAvaliacao; 
        this.parecer = parecer; 
        this.dataParecer = dataParecer; 
        this.consideracoes = consideracoes; 
        this.dataConsideracoes = dataConsideracoes; 
	}
}