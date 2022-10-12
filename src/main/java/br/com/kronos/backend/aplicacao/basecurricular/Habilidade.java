

package br.com.kronos.backend.aplicacao.basecurricular;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Habilidade {
    
    private long id;
    private Long idComponente;
    private String descricao;
    private String codigo;
    private boolean ativo;
    private Boolean bncc;
    private String eixo;
    private String campoAtuacao;
    private String praticaLinguagem;
    private String objetoConhecimento;
    private String unidadeTematica;
    private Long idInstituicao;

	@Builder
    public Habilidade(long id, Long idComponente, String descricao, String codigo, boolean ativo, Boolean bncc, String eixo, 
          String campoAtuacao, String praticaLinguagem, String objetoConhecimento, String unidadeTematica, Long idInstituicao) {

		super();
        this.id = id;
        this.idComponente = exigirNaoNulo(idComponente, "Componente"); 
        this.descricao = exigirTamanho(descricao, "Descrição", 1, 1500); 
        this.codigo = exigirTamanho(codigo, "Código", 1, 10); 
        this.ativo = exigirNaoNulo(ativo, "Ativo");
        this.eixo = exigirTamanho(eixo, "Eixo", 0, 100);  
        this.campoAtuacao = exigirTamanho(campoAtuacao, "Campo Atuação", 0, 300);  
        this.praticaLinguagem = exigirTamanho(praticaLinguagem, "Prática da Linguagem", 0, 300);   
        this.objetoConhecimento = exigirTamanho(objetoConhecimento, "Objeto Conhecimento", 0, 500); 
        this.unidadeTematica = exigirTamanho(unidadeTematica, "Unidade Temática", 0, 300);
        if (bncc != null && bncc) {
        	this.bncc = bncc;
        	this.idInstituicao = null; 
        } else {
        	this.bncc = false;
        	this.idInstituicao = idInstituicao;     
        }    
	}
}