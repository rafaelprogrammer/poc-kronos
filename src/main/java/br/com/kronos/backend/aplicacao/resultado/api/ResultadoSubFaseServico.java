package br.com.kronos.backend.aplicacao.resultado.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.calendario.FiltroSubFaseExecucao;
import br.com.kronos.backend.aplicacao.calendario.api.EstatisticaResultadoSubFaseDTO;
import br.com.kronos.backend.aplicacao.calendario.api.PeriodoExecucaoResumoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.SubFaseExecucaoResumoDTO;
import br.com.kronos.backend.aplicacao.curso.api.CursoResumoDTO;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaResumoDTO;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaResumoDTO;
import br.com.kronos.backend.aplicacao.resultado.FiltroResultadoSubFase;


public interface ResultadoSubFaseServico {
	
	void criar(List<ResultadoSubFaseDTO> resultadoSubFaseDTO);
	List<ResultadoSubFaseDTO>listarAlunosResultados(FiltroResultadoSubFase filtroResultadoSubFase);
	boolean excluir(Long id);
	List<CursoResumoDTO> listarComboCurso(FiltroSubFaseExecucao filtroSubFaseExecucao);
	List<PeriodoExecucaoResumoDTO> listarComboPeriodoExecucao(FiltroSubFaseExecucao filtroSubFaseExecucao);
	List<TurmaResumoDTO> listarComboTurma(FiltroSubFaseExecucao filtroSubFaseExecucao);
	List<DisciplinaResumoDTO> listarComboDisciplina(FiltroSubFaseExecucao filtroSubFaseExecucao);
	List<SubFaseExecucaoResumoDTO> listarComboSubFaseExecucao(FiltroSubFaseExecucao filtroSubFaseExecucao);
	EstatisticaResultadoSubFaseDTO recuperarEstatistasResultado(FiltroSubFaseExecucao filtroSubFaseExecucao);
	List<ResultadoAvaliacaoDTO> listarResultadosAvaliacoes(FiltroResultadoSubFase filtro);
	
}

