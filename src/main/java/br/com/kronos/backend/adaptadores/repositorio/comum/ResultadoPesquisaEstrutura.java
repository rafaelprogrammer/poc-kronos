package br.com.kronos.backend.adaptadores.repositorio.comum;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResultadoPesquisaEstrutura {

	private Long idPeriodo;
	private String nomePeriodo;
	private Long idFase;
	private String nomeFase;
	private Long idSubFase;
	private String nomeSubFase;
	
}
