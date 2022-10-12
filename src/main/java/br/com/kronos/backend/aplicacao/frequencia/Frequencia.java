
package br.com.kronos.backend.aplicacao.frequencia;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Frequencia {
    
    private Long id;
    private LocalDate dataReposicao; 
    private Long idAtividade;
    private Long idCredito;
    private int numeroPresenca;
    private int numeroFalta;
    private int numeroFaltaJustificada;
    private String[] frequencia;

	@Builder
    public Frequencia(Long id, LocalDate dataReposicao, Long idAtividade, Long idCredito, String[] frequencia, int numeroPresenca, 
    		int numeroFalta, int numeroFaltaJustificada) {

		super();
        this.id = id;
        this.dataReposicao = dataReposicao; 
        this.numeroPresenca = numeroPresenca;
        this.numeroFalta = numeroFalta;
        this.numeroFaltaJustificada = numeroFaltaJustificada;
        this.idAtividade = exigirNaoNulo(idAtividade, "Atividade"); 
        this.idCredito = exigirNaoNulo(idCredito, "Aluno"); 
        this.frequencia = exigirNaoNulo(frequencia, "Frequencia"); 
	}
	
	
	public void atualizarNumeroPresenca(int numeroPresenca) {
		this.numeroPresenca = numeroPresenca;
	}
	
	public void atualizarNumeroFalta(int numeroFalta) {
		this.numeroFalta = numeroFalta;
	}
	
	public void atualizarNumeroFaltaJustificada(int numeroFaltaJustificada) {
		this.numeroFaltaJustificada = numeroFaltaJustificada;
	}
	
	public void atualizarDataReposicao(LocalDate dataReposicao) {
		this.dataReposicao = dataReposicao;
	}
	
	public void atualizarArrayFrequencia(String[] frequencia) {
		this.frequencia = frequencia;
	}
}