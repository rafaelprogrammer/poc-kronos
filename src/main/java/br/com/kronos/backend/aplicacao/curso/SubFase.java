package br.com.kronos.backend.aplicacao.curso;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SubFase {
    
    private long id;
    private Integer numero;
    private String nome;
    private String sigla;
    private Integer idTipoPeriodo;
    private Long idFase;

    @Builder
	public SubFase(long id, Integer numero, String nome, String sigla, Integer idTipoPeriodo, Long idFase) {

        this.id = id;
        this.numero = exigirNaoNulo(numero, "Número");
        this.nome = exigirTamanho(nome, "Nome", 1, 30);
        this.sigla = exigirTamanho(sigla, "Sigla", 1, 10);  
        this.idTipoPeriodo = exigirNaoNulo(idTipoPeriodo, "Tipo de período"); 
        this.idFase = exigirNaoNulo(idFase, "Fase (Semestre) ");  
	}
}
