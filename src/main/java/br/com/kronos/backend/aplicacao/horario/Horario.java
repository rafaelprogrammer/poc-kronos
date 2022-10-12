
package br.com.kronos.backend.aplicacao.horario;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Horario {
    
    private Long id;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private boolean regular;
    private Long idFuncionario;
    private Long idDisciplina;
    private Long idTurma;
    private int idTipoDiaSemana;
    private int idTipoRegimeLetivo;

	@Builder
    public Horario(Long id, LocalDate dataInicial, LocalDate dataFinal, boolean regular, Long idFuncionario, 
                   Long idDisciplina, Long idTurma, int idTipoDiaSemana, int idTipoRegimeLetivo) {

		super();
        this.id = id;
        this.dataInicial = exigirNaoNulo(dataInicial, "Data Inicial");
        this.dataFinal = exigirNaoNulo(dataFinal, "Data Final");
        this.regular = regular; 
        this.idFuncionario = exigirNaoNulo(idFuncionario, "Funcionário (professor)"); 
        this.idDisciplina = exigirNaoNulo(idDisciplina, "Disciplina"); 
        this.idTurma = exigirNaoNulo(idTurma, "Turma"); 
        this.idTipoDiaSemana = exigirNaoNulo(idTipoDiaSemana, "Dia da semana"); 
        this.idTipoRegimeLetivo = exigirNaoNulo(idTipoRegimeLetivo, "Tipo de regime letivo");
	}
}