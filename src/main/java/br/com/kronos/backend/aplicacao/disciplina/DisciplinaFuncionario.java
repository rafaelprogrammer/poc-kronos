package br.com.kronos.backend.aplicacao.disciplina;

import lombok.Builder;
import lombok.Getter;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

@Getter
public class DisciplinaFuncionario {

	private Long idDisciplina;
	private Long idFuncionario;
	
	@Builder
	public DisciplinaFuncionario(Long idDisciplina, Long idFuncionario) {
		
		this.idDisciplina = exigirNaoNulo(idDisciplina, "Disciplina"); 
		this.idFuncionario = exigirNaoNulo(idFuncionario, "Funcionário");
	}
	
}
