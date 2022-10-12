package br.com.kronos.backend.aplicacao.instituicao;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Mencao {
    
    private Long id;
    private String nome;
    private String simbolo;

	@Builder
	public Mencao(Long id, String nome, String simbolo) {

		super();
        this.id = id;
        this.nome = exigirNaoNulo(nome, "Nome");
        this.simbolo = exigirNaoNulo(simbolo, "Símbolo da menção");     
	}
}