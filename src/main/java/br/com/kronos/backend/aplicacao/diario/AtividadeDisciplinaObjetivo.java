package br.com.kronos.backend.aplicacao.diario;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AtividadeDisciplinaObjetivo {

    private Long idDisciplinaObjetivo;
    private Long idAtividade;
    
    @Builder
    public AtividadeDisciplinaObjetivo(long idDisciplinaObjetivo, long idAtividade) {

        this.idDisciplinaObjetivo = exigirNaoNulo(idDisciplinaObjetivo, "Disciplina Objetivo"); 
        this.idAtividade = exigirNaoNulo(idAtividade, "Atividade"); 
	}

}
