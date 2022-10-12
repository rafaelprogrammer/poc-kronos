package br.com.kronos.backend.adaptadores.repositorio.calendario;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResultadoPesquisaEstruturaPeriodoExecucao {

	private Long idPeriodo;
	private String periodo;
	private Long idFase;
	private String fase;
	private Long idSubFase;
	private String subFase;
	
}
