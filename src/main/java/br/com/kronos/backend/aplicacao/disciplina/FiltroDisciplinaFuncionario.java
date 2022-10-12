package br.com.kronos.backend.aplicacao.disciplina;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroDisciplinaFuncionario {

    private Long idDisciplina;
    private Long idFuncionario;
	private Integer qtdTotal;
	private Integer numeroPagina;
}