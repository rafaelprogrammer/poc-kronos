
package br.com.kronos.backend.aplicacao.calendario;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PeriodoExecucao {
	
	public static final String COMBO_CACHE_NAME_PERFIL_PROFESSORES = "combo_periodos_execucoes_perfil_professores";
    
    private long id; 
    private Long idCalendario;
    private Long idPeriodo;
    private Integer ano;
 
	@Builder
    public PeriodoExecucao(long id, Long idCalendario, Long idPeriodo, Integer ano) {

		super();
        this.id = id;
        this.idCalendario = exigirNaoNulo(idCalendario, "Calendário"); 
        this.idPeriodo = exigirNaoNulo(idPeriodo, "Período"); 
        this.ano = exigirNaoNulo(ano, "Ano");
	}
}