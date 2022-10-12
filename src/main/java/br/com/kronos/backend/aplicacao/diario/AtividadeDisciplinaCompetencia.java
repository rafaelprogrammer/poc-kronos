package br.com.kronos.backend.aplicacao.diario;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AtividadeDisciplinaCompetencia {

    private Long idDisciplinaCompetencia;
    private Long idAtividade;
    
    @Builder
    public AtividadeDisciplinaCompetencia(long idDisciplinaCompetencia, long idAtividade) {

        this.idDisciplinaCompetencia = exigirNaoNulo(idDisciplinaCompetencia, "Disciplina Competência"); 
        this.idAtividade = exigirNaoNulo(idAtividade, "Atividade"); 
	}

}
