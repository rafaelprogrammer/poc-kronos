package br.com.kronos.backend.aplicacao.basecurricular;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Direito {
    
    private long id;
    private String descricao;
    private Long idNivel;
    private String codigo;
    private boolean ativo;
    private Boolean bncc;
    private Long idInstituicao;

	@Builder
	public Direito(long id, String descricao, Long idNivel, String codigo, boolean ativo, Boolean bncc, Long idInstituicao) {

		super();
        this.id = id;
        this.descricao = exigirTamanho(descricao, "Descrição", 1, 400);
        this.idNivel = exigirNaoNulo(idNivel, "Nível"); 
        this.codigo = exigirTamanho(codigo, "Código", 1, 5);
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