package br.com.kronos.backend.aplicacao.curso;

import lombok.Builder;
import lombok.Getter;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

@Getter
public class CursoEscala {

	private Long idCurso;
	private Long idEscala;
	
	@Builder
	public CursoEscala(Long idCurso, Long idEscala) {
		
		this.idCurso = exigirNaoNulo(idCurso, "Curso"); 
		this.idEscala = exigirNaoNulo(idEscala, "Escala");
	}
	
}
