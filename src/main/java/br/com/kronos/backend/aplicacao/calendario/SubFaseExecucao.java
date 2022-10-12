
package br.com.kronos.backend.aplicacao.calendario;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SubFaseExecucao {
    
	public static final String COMBO_CACHE_NAME_PERFIL_PROFESSORES = "combo_subfases_perfil_professores";
	
    private Long id;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Long idFaseExecucao;
    private Long idSubFase;

    
 
	@Builder
    public SubFaseExecucao(Long id, LocalDate dataInicio, LocalDate dataFim, Long idFaseExecucao, Long idSubFase) {

		super();
        this.id = id;
        this.dataInicio = exigirNaoNulo(dataInicio, "Data de início"); 
        this.dataFim = exigirNaoNulo(dataFim, "Data final"); 
        this.idFaseExecucao = exigirNaoNulo(idFaseExecucao, "Fase (Semestre) de execução"); 
        this.idSubFase = exigirNaoNulo(idSubFase, "Sub-Fase (Bimestre)"); 
	}
}