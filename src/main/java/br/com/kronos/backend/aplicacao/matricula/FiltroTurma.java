package br.com.kronos.backend.aplicacao.matricula;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroTurma {

    private Long id;     
    private Integer ano;
    private Boolean aberta;
    private String sigla;
    private Long idPeriodoExecucao;
    private Long idInstituicao;
    private Long idCurso;
    private Long idCalendario;
	private Integer qtdTotal;
	private Integer numeroPagina;
}