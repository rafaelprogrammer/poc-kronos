package br.com.kronos.backend.aplicacao.curso;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CursoFuncionario {
    
    private long id;
    private LocalDate dataInicio;
    private LocalDate dataFinal;
    private Long idFuncionario;
    private Long idCurso;
    private Integer idTipoFuncao;

	@Builder
	public CursoFuncionario(long id, LocalDate dataInicio, LocalDate dataFinal, Long idFuncionario, Long idCurso, Integer idTipoFuncao) {

		super();
        this.id = id;
        this.dataInicio = exigirNaoNulo(dataInicio, "Data de início");
        this.dataFinal = dataFinal;
        this.idFuncionario = exigirNaoNulo(idFuncionario, "Funcionário");
        this.idCurso = exigirNaoNulo(idCurso, "Curso");  
        this.idTipoFuncao = exigirNaoNulo(idTipoFuncao, "Tipo de função");       
	}
}
