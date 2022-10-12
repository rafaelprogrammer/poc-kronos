package br.com.kronos.backend.aplicacao.instituicao;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Setor {
	private Long id;
	private String nome;
	private boolean ativo;
    private Long idInstitucicao;
    	
	@Builder
	public Setor(Long id, String nome, boolean ativo, Long idInstitucicao) {

		super();
		this.id = id;
		this.nome = exigirNaoNulo(nome, "Nome");
		this.ativo = exigirNaoNulo(ativo, "Ativo");
		this.idInstitucicao = exigirNaoNulo(idInstitucicao, "Instituição");
	}
}
