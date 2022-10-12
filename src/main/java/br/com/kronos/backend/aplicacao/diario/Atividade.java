
package br.com.kronos.backend.aplicacao.diario;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Atividade {
	
	public static final String CACHE_NAME = "atividades";
	public static final String CACHE_NAME_COUNT = "count_atividades";
	
    
    private long id;
    private LocalDate dataPrevista;
    private LocalDate dataRealizacao;  
    private Long idHorario;
    private Long idSubFaseExecucao;
    private Long idFuncionario;

	@Builder
    public Atividade(long id, LocalDate dataPrevista, LocalDate dataRealizacao, Long idHorario, Long idSubFaseExecucao, Long idFuncionario) {

        this.id = id;
        this.dataPrevista = exigirNaoNulo(dataPrevista, "Data prevista"); 
        this.dataRealizacao = dataRealizacao; 
        this.idHorario = idHorario; 
        this.idSubFaseExecucao = exigirNaoNulo(idSubFaseExecucao, "Sub-fase de execução"); 
        this.idFuncionario = exigirNaoNulo(idFuncionario, "Funcionário"); 
	}
}