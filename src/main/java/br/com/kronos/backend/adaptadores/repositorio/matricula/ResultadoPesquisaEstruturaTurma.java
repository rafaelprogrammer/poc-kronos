package br.com.kronos.backend.adaptadores.repositorio.matricula;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResultadoPesquisaEstruturaTurma {

	private Long idPeriodo;
	private String nomePeriodo;
	private Long idTurma;
	private String nomeTurma;
	private String turno;
	
}
