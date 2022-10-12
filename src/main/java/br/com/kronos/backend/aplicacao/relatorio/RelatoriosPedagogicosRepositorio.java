package br.com.kronos.backend.aplicacao.relatorio;

import java.util.List;

import br.com.kronos.backend.aplicacao.calendario.api.SubFaseExecucaoDTO;
import br.com.kronos.backend.aplicacao.comum.FiltroRelatorio;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaResumoDTO;

public interface RelatoriosPedagogicosRepositorio {
	
	List<SubFaseExecucaoDTO> listarBimestres(FiltroRelatorio filtro);
	
	List<DisciplinaResumoDTO> listarDisciplinasProfessores(FiltroRelatorio filtro);

	Long encerrarDiario(Long idSubFaseExecucao, Long idDisciplina);

	boolean reabrirDiario(Long idSubFaseExecucao, Long idDisciplina);

	List<DisciplinaResumoDTO> listarDisciplinasProfessoresResultadosBimestrais(FiltroRelatorio filtro);

	boolean atualizarResultadosBimestrais(FiltroRelatorio filtro);
	
}
