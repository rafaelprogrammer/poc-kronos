package br.com.kronos.backend.aplicacao.basecurricular;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Componente {
    
    private long id;
    private String nome;
    private Long idNivel;
    private boolean ativo;
    private Boolean bncc;
    private Long idInstituicao;

	@Builder
	public Componente(long id, String nome, Long idNivel, boolean ativo, Boolean bncc, Long idInstituicao) {

		super();
        this.id = id;
        this.nome = exigirTamanho(nome, "Nome", 1, 100); 
        this.idNivel = exigirNaoNulo(idNivel, "Nível");
        this.ativo = ativo;
        if (bncc != null && bncc) {
        	this.bncc = bncc;
        	this.idInstituicao = null; 
        } else {
        	this.bncc = false;
        	this.idInstituicao = idInstituicao;     
        }
	}
}