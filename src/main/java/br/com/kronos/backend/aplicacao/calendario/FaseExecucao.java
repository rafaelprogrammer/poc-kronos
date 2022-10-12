
package br.com.kronos.backend.aplicacao.calendario;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FaseExecucao {
    
    private long id;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Long idPeriodoExecucao;
    private Long idFase;

    
 
	@Builder
    public FaseExecucao(long id, LocalDate dataInicio, LocalDate dataFim, Long idPeriodoExecucao, Long idFase) {

        this.id = id;
        this.dataInicio = exigirNaoNulo(dataInicio, "Data de início"); 
        this.dataFim = exigirNaoNulo(dataFim, "Data final"); 
        this.idPeriodoExecucao = exigirNaoNulo(idPeriodoExecucao, "Período (Ano) de execução"); 
        this.idFase = exigirNaoNulo(idFase, "Fase (Semestre)"); 
	}
}