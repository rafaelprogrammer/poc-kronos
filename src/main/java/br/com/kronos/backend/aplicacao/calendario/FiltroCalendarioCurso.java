package br.com.kronos.backend.aplicacao.calendario;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroCalendarioCurso {

    private Long idCalendario;
    private Long idCurso;
    private Long idInstituicao;
    private Integer ano;
	private Integer qtdTotal;
	private Integer numeroPagina;
}