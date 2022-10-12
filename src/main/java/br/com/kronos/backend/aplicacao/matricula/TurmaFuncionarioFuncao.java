package br.com.kronos.backend.aplicacao.matricula;


import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TurmaFuncionarioFuncao {
    
    private Long id;
    private LocalDate dataInicioVigencia;
    private LocalDate dataFinalVigencia;
    private Long idFuncionario;
    private Long idTurma;
    private int idTipoFuncao;

	@Builder
    public TurmaFuncionarioFuncao(Long id, LocalDate dataInicioVigencia, LocalDate dataFinalVigencia, Long idFuncionario, 
                                  Long idTurma, int idTipoFuncao) {

		super();
        this.id = id;
        this.dataInicioVigencia = exigirNaoNulo(dataInicioVigencia, "Data de início da vigência");  
        this.dataFinalVigencia = exigirNaoNulo(dataFinalVigencia, "Data final da vigência");
        this.idFuncionario = exigirNaoNulo(idFuncionario, "Funcionário");
        this.idTurma = exigirNaoNulo(idTurma, "Turma"); 
        this.idTipoFuncao = exigirNaoNulo(idTipoFuncao, "Tipo da função"); 

	}
}