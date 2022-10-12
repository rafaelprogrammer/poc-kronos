
package br.com.kronos.backend.aplicacao.basecurricular;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Valor {
    
    private long id;
    private String nome;
    private String codigo;
    private Long idInstituicao;
    private boolean ativo;
    

	@Builder
    public Valor(long id, String nome, String codigo, Long idInstituicao, boolean ativo) {

		super();
        this.id = id;
        this.nome = exigirTamanho(nome, "Nome", 1, 20);
        this.codigo = exigirTamanho(codigo, "Código", 1, 5); 
        this.idInstituicao = exigirNaoNulo(idInstituicao, "Instituição");
        this.ativo = exigirNaoNulo(ativo, "Ativo");  
	}
}