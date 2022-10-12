
package br.com.kronos.backend.aplicacao.avaliacao;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GrupoAvaliacao {
    
    private long id;
    private Integer numero;
    private Long idAvaliacao;
    private String tema;

	@Builder
    public GrupoAvaliacao(long id, Integer numero, Long idAvaliacao, String tema) {
        this.id = id;
        this.numero = exigirNaoNulo(numero, "Número"); 
        this.idAvaliacao = exigirNaoNulo(idAvaliacao, "Avaliação"); 
        this.tema = exigirTamanho(tema, "Tema", 0, 100); 
	}
}