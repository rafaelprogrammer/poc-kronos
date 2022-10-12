package br.com.kronos.backend.aplicacao.diario;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AtividadeDisciplinaDireito {

    private Long idDisciplinaDireito;
    private Long idAtividade;
    
    @Builder
    public AtividadeDisciplinaDireito(long idDisciplinaDireito, long idAtividade) {

        this.idDisciplinaDireito = exigirNaoNulo(idDisciplinaDireito, "Disciplina Direito"); 
        this.idAtividade = exigirNaoNulo(idAtividade, "Atividade"); 
	}

}
