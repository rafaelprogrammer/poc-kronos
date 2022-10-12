package br.com.kronos.backend.aplicacao.curso;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;


@ToString
@EqualsAndHashCode(of = {"id", "nome"})
public class Estrutura {

	@Getter
	private final Long id;
	@Getter
	private final String nome;
	private final List<Estrutura> filhos;
	
	public static Estrutura comNome(String nome) {
		// @formatter:off
		return Estrutura.builder()
				.nome(nome)
				.build();
		// @formatter:on
	}
	
	public static Estrutura comIdENome(Long id, String nome) {
		// @formatter:off
		return Estrutura.builder()
				.id(id)
				.nome(nome)
				.build();
		// @formatter:on
	}
	
	@Builder
	private Estrutura(Long id, String nome) {
		this.id = id;
		this.nome = nome;
		this.filhos = new ArrayList<>();
	}
	
	public void adicionarFilhos(List<Estrutura> filhos) {
		for (Estrutura filho : filhos) {
			adicionarFilho(filho);
		}
	}
	
	public void adicionarFilho(@NonNull Estrutura filho) {
		this.filhos.add(filho);
	}
	
	public List<Estrutura> getFilhos() {
		return this.filhos;
	}
}
