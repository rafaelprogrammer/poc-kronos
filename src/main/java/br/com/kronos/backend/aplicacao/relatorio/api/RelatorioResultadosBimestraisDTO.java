package br.com.kronos.backend.aplicacao.relatorio.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RelatorioResultadosBimestraisDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer ano;
	private Long idCurso;
	private Long idPeriodoExecucao;
	private Long idSubFaseExecucao;
	private Long idDisciplina;
	private Long idTurma;
	private Long idFuncionario;

}
