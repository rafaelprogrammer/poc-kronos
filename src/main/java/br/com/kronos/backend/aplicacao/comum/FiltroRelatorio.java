package br.com.kronos.backend.aplicacao.comum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroRelatorio {

	private Integer ano;
	private Long idCurso;
	private Long idPeriodoExecucao;
	private Long idSubFaseExecucao;
	private Long idDisciplina;
	private Long idTurma;
	private Long idFuncionario;
	private Integer[] idsTipoSituacaoContrato;
	private Integer qtdTotal;
	private Integer numeroPagina;
}
