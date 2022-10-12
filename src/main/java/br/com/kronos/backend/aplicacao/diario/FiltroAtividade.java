package br.com.kronos.backend.aplicacao.diario;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
@Builder
public class FiltroAtividade{

    private Long id;  
    private LocalDate dataPrevista;
    private LocalDate dataRealizacao;  
    private Long idHorario;
    private Long idSubFaseExecucao;
    private Long idFuncionario;
    private Long idPessoaUsuarioLogado;
    private Long idDisciplina;
    private Long idTurma;
    private Long idCalendario;
    private Long idPeriodoExecucao;
	private Integer qtdTotal;
	private Integer numeroPagina;
	private Integer anoTurma;
}