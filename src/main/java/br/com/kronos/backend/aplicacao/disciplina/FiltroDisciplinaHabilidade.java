package br.com.kronos.backend.aplicacao.disciplina;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroDisciplinaHabilidade {

	private Long id;
	private Long idDisciplina;
	private Long idHabilidade;
	private Long idAtividade;
	private Long idAvaliacao;
	private Long idSubFase;
	private String descricaoHabilidade;
	private String codigoHabilidade;
	private LocalDate dataInicioVigencia;
	private LocalDate dataFinalVigencia;
	private Integer qtdTotal;
	private Integer numeroPagina;
}

