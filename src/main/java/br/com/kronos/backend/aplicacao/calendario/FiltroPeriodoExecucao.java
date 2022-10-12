package br.com.kronos.backend.aplicacao.calendario;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroPeriodoExecucao {

    private Long id;  
    private Long idCalendario;
    private Long idPeriodo;
    private Long idInstituicao;
    private Long idCurso;
    private Integer ano;
	private Integer qtdTotal;
	private Integer numeroPagina;
}