package br.com.kronos.backend.aplicacao.resultado;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroResultadoSubFase{

    private Long id;  
    private Long idSubFaseExecucao;
    private Long idCredito;
    private Long idTurma;
    private Long idDisciplina;
    private LocalDate dataInicio;
    private LocalDate dataFim;
	private Integer qtdTotal;
	private Integer numeroPagina;
}