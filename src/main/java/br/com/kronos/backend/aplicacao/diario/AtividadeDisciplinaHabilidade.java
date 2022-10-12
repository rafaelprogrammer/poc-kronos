package br.com.kronos.backend.aplicacao.diario;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AtividadeDisciplinaHabilidade {

    private Long idDisciplinaHabilidade;
    private Long idAtividade;
    
    @Builder
    public AtividadeDisciplinaHabilidade(long idDisciplinaHabilidade, long idAtividade) {

        this.idDisciplinaHabilidade = exigirNaoNulo(idDisciplinaHabilidade, "Disciplina Habilidade"); 
        this.idAtividade = exigirNaoNulo(idAtividade, "Atividade"); 
	}

}
