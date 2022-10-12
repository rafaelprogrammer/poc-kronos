package br.com.kronos.backend.aplicacao.basecurricular;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Competencia {
    
    private long id;
    private String descricao;
    private Long idNivel;
    private Long idComponente;
    private String codigo;
    private boolean ativo;
    private Boolean bncc;
    private Boolean geral;
    private Long idInstituicao;

	@Builder
    public Competencia(long id, String descricao, Long idNivel, Long idComponente, String codigo, 
                       boolean ativo, Boolean bncc, Boolean geral, Long idInstituicao) {

		super();
        this.id = id;
        this.descricao = exigirTamanho(descricao, "Descrição", 1, 500);
        this.idNivel = exigirNaoNulo(idNivel, "Nível");    
        
        this.codigo = exigirTamanho(codigo, "Código", 1, 10);
        this.ativo = exigirNaoNulo(ativo, "Ativo");
        if (bncc != null && bncc) {
        	this.bncc = bncc;
        	this.idInstituicao = null; 
        } else {
        	this.bncc = false;
        	this.idInstituicao = idInstituicao;     
        }
        if (geral != null && geral) {
        	this.geral = geral;
        	this.idComponente = null;  
        } else {
        	this.geral = false;
        	this.idComponente = idComponente;  
        }
	}
}
