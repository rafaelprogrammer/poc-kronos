package br.com.kronos.backend.aplicacao.disciplina;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroDisciplinaCompetencia {

	private Long id;
	private Long idDisciplina;
	private Long idCompetencia;
	private Long idAtividade;
	private Long idSubFase;
	private String codigo;
	private String descricao;
	private LocalDate dataInicioVigencia;
	private LocalDate dataFinalVigencia;
	private Integer qtdTotal;
	private Integer numeroPagina;
}

