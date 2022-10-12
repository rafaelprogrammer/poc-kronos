package br.com.kronos.backend.aplicacao.curso;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TipoMatrizHorario {
    
    private long id;
    private String nome;

	@Builder
	public TipoMatrizHorario(long id, String nome) {
        this.id = id;
        this.nome = exigirTamanho(nome, "Nome", 1, 30);
	}
}