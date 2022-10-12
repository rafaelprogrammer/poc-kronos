package br.com.kronos.backend.aplicacao.basecurricular;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Nivel {
    
    private int id;
    private String nome;

	@Builder
	public Nivel(int id, String nome) {

		super();
        this.id = id;
        this.nome = exigirTamanho(nome, "Nome", 1, 100);    
	}
}