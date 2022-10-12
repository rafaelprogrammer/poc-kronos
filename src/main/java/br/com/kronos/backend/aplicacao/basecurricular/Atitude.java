
package br.com.kronos.backend.aplicacao.basecurricular;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Atitude {
    
    private long id;
    private String nome;
    private String codigo;
    private Long idInstituicao;
    private Long idValor;
    private boolean ativo;
    
	@Builder
    public Atitude(long id, String nome, String codigo, Long idInstituicao, Long idValor, boolean ativo) {

		super();
        this.id = id;
        this.nome = exigirTamanho(nome, "Nome", 1, 300);
        this.codigo = exigirTamanho(codigo, "Codigo", 1, 5);
        this.idInstituicao = exigirNaoNulo(idInstituicao, "Instituição");
        this.idValor = exigirNaoNulo(idValor, "Valor");
        this.ativo = exigirNaoNulo(ativo, "Ativo");  
	}
}