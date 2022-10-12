package br.com.kronos.backend.aplicacao.basecurricular;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Objetivo {
    
    private long id;
    private Long idCampoExperiencia;
    private String descricao;
    private String codigo;
    private boolean ativo;
    private Boolean bncc;
    private Long idInstituicao;
    private Long idFaixaAno;

	@Builder
    public Objetivo(long id, Long idCampoExperiencia, String descricao, String codigo, boolean ativo, Boolean bncc, 
                    Long idInstituicao, Long idFaixaAno) {

		super();
        this.id = id;
        this.idCampoExperiencia = exigirNaoNulo(idCampoExperiencia, "Campo de experiência"); 
        this.descricao = exigirTamanho(descricao, "Descrição", 1, 400);   
        this.codigo = exigirTamanho(codigo, "Código", 1, 10); 
        this.ativo = exigirNaoNulo(ativo, "Ativo");
        if (bncc != null && bncc) {
        	this.bncc = bncc;
        	this.idInstituicao = null; 
        } else {
        	this.bncc = false;
        	this.idInstituicao = idInstituicao;     
        }
        this.idFaixaAno = exigirNaoNulo(idFaixaAno, "Faixa / Ano");    
	}
}