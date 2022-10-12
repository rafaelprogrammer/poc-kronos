
package br.com.kronos.backend.aplicacao.avaliacao;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AvaliacaoHabilidade {
    
    private long id;
    private Long idAvaliacao;
    private Long idDisciplinaHabilidade;

	@Builder
    public AvaliacaoHabilidade(long id, Long idAvaliacao, Long idDisciplinaHabilidade) {

        this.id = id;
        this.idAvaliacao = exigirNaoNulo(idAvaliacao, "Avaliação"); 
        this.idDisciplinaHabilidade = exigirNaoNulo(idDisciplinaHabilidade, "Disciplina Habilidade"); 
	}
}