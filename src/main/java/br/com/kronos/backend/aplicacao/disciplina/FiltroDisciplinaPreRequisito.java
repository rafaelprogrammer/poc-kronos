package br.com.kronos.backend.aplicacao.disciplina;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroDisciplinaPreRequisito {

    private Long idDisciplina;
    private Long idDisciplinaPreRequisito;
	private Integer qtdTotal;
	private Integer numeroPagina;
}