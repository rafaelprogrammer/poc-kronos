package br.com.kronos.backend.aplicacao.matricula;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TurmaSala {
    
    private Long id;
    private boolean principal;
    private Long idSala;
    private Long idTurma;

	@Builder
    public TurmaSala(Long id, boolean principal, Long idSala, Long idTurma) {

		super();
        this.id = id;
        this.principal = exigirNaoNulo(principal, "Principal");  
        this.idSala = exigirNaoNulo(idSala, "Sala");
        this.idTurma = exigirNaoNulo(idTurma, "Turma");
	}
}