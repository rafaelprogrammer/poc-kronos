package br.com.kronos.backend.aplicacao.curso;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Periodo {
    
    private long id;
    private Integer numero;
    private String nome;
    private String sigla;
    private Long idCurso;
    private Integer idTipoPeriodo;
    private Long idFaixaAno;
    private Double valor;

	@Builder
	public Periodo(long id, int numero, String nome, String sigla, Long idCurso, int idTipoPeriodo, Long idFaixaAno, Double valor) {

		super();
        this.id = id;
        this.numero = exigirNaoNulo(numero, "Número");
        this.nome = exigirTamanho(nome, "Nome", 1, 30);
        this.sigla = exigirTamanho(sigla, "Sigla", 1, 10);  
        this.idCurso = exigirNaoNulo(idCurso, "Curso");
        this.idTipoPeriodo = exigirNaoNulo(idTipoPeriodo, "Tipo de período"); 
        this.idFaixaAno = exigirNaoNulo(idFaixaAno, "Faixa/Ano");
        this.valor = exigirNaoNulo(valor, "Valor");  
	}
}