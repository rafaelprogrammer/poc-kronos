package br.com.kronos.backend.aplicacao.calendario;

import lombok.Builder;
import lombok.Getter;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

@Getter
public class CalendarioCurso {

	private Long idCalendario;
	private Long idCurso;
	
	@Builder
	public CalendarioCurso(Long idCalendario, Long idCurso) {
		
		this.idCalendario = exigirNaoNulo(idCalendario, "Calendário"); 
		this.idCurso = exigirNaoNulo(idCurso, "Curso");
	}
	
}
