package br.com.kronos.backend.aplicacao.basecurricular;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FaixaAno {
    
    private int id;
    private String nome;
    private Integer idNivel;

	@Builder
	public FaixaAno(int id, String nome, Integer idNivel) {

		super();
        this.id = id;
        this.nome = exigirTamanho(nome, "Nome", 1, 100);  
        this.idNivel = exigirNaoNulo(idNivel, "Nível");   
	}
}