package br.com.kronos.backend.aplicacao.calendario;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroSubFaseExecucao {

    private Long id;  
    private Long idFaseExecucao;
    private Long idSubFase;
    private Long idCurso;
    private Long idInstituicao;
    private Long idPeriodoExecucao;
    private Long idPessoaUsuarioLogado;
    private Long idDisciplina;
    private Long idTurma;
    private Long idCredito;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Integer anoPeriodoExecucao;
	private Integer qtdTotal;
	private Integer numeroPagina;
}