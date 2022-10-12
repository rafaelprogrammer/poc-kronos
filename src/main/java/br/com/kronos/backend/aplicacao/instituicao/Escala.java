package br.com.kronos.backend.aplicacao.instituicao;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirTamanho;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Escala {
    
    private long id;
    private String nome;
    private LocalDate dataInicialVigencia;
    private LocalDate dataFinalVigencia; 
    private Long idInstituicao;
    private int idTipoAbrangencia;  
    private int idTipoAvaliacao;

	@Builder
	public Escala(long id, String nome, LocalDate dataInicialVigencia, LocalDate dataFinalVigencia, Long idInstituicao, 
                    int idTipoAbrangencia, int idTipoAvaliacao) {

		super();
        this.id = id;
        this.nome = exigirTamanho(nome, "Nome da escala", 1, 40);
        this.dataInicialVigencia = exigirNaoNulo(dataInicialVigencia, "Data de início de vigência");
        this.dataFinalVigencia = dataFinalVigencia;
        this.idInstituicao = exigirNaoNulo(idInstituicao, "Instituição");
        this.idTipoAbrangencia = exigirNaoNulo(idTipoAbrangencia, "Tipo de abrangência");  
        this.idTipoAvaliacao = exigirNaoNulo(idTipoAvaliacao, "Tipo de avaliação");       
	}
}