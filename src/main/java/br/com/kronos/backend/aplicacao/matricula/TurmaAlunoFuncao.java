package br.com.kronos.backend.aplicacao.matricula;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TurmaAlunoFuncao {
    
    private Long id;
    private LocalDate dataInicioVigencia;
    private LocalDate dataFinalVigencia;
    private Long idMatricula;
    private Long idTurma;
    private int idTipoFuncao;

	@Builder
    public TurmaAlunoFuncao(Long id, LocalDate dataInicioVigencia, LocalDate dataFinalVigencia, Long idMatricula, 
    		                Long idTurma, int idTipoFuncao) {

		super();
        this.id = id;
        this.dataInicioVigencia = exigirNaoNulo(dataInicioVigencia, "Data de início da vigência");  
        this.dataFinalVigencia = exigirNaoNulo(dataFinalVigencia, "Data final da vigência");
        this.idMatricula = exigirNaoNulo(idMatricula, "Matrícula");
        this.idTurma = exigirNaoNulo(idTurma, "Turma"); 
        this.idTipoFuncao = exigirNaoNulo(idTipoFuncao, "Tipo da função"); 
	}
}