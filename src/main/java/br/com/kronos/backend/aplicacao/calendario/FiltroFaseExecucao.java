package br.com.kronos.backend.aplicacao.calendario;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroFaseExecucao {

    private Long id;  
    private Long idPeriodoExecucao;
    private Long idFase;
    private Long idCurso;
    private Long idInstituicao;
    private Integer ano;
	private Integer qtdTotal;
	private Integer numeroPagina;
}