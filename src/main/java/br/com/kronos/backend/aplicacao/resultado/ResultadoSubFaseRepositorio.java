package br.com.kronos.backend.aplicacao.resultado;

import java.util.List;

import br.com.kronos.backend.aplicacao.calendario.FiltroSubFaseExecucao;
import br.com.kronos.backend.aplicacao.calendario.api.PeriodoExecucaoResumoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.SubFaseExecucaoResumoDTO;
import br.com.kronos.backend.aplicacao.curso.api.CursoResumoDTO;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaResumoDTO;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaResumoDTO;
import br.com.kronos.backend.aplicacao.resultado.api.ResultadoAvaliacaoDTO;
import br.com.kronos.backend.aplicacao.resultado.api.ResultadoSubFaseDTO;

public interface ResultadoSubFaseRepositorio {
	
	Long criar(ResultadoSubFase resultadoSubFase);
	Long alterar(ResultadoSubFase resultadoSubFase);
	List<ResultadoSubFaseDTO> listarAlunosResultados(FiltroResultadoSubFase filtroResultadoSubFase);
	boolean excluir(Long id);
	int contarAlunosResultados(FiltroResultadoSubFase filtroResultadoSubFase);
	List<CursoResumoDTO> listarComboCurso(FiltroSubFaseExecucao filtroSubFaseExecucao);
	List<PeriodoExecucaoResumoDTO> listarComboPeriodoExecucao(FiltroSubFaseExecucao filtroSubFaseExecucao);
	List<TurmaResumoDTO> listarComboTurma(FiltroSubFaseExecucao filtroSubFaseExecucao);
	List<DisciplinaResumoDTO> listarComboDisciplina(FiltroSubFaseExecucao filtroSubFaseExecucao);
	List<SubFaseExecucaoResumoDTO> listarComboSubFaseExecucao(FiltroSubFaseExecucao filtroSubFaseExecucao);
	int contarAulasDadas(FiltroSubFaseExecucao filtroSubFaseExecucao);
	int contarNrHabilidadesTrabalhadas(FiltroSubFaseExecucao filtroSubFaseExecucao);
	int contarNrHabilidadesAvaliadas(FiltroSubFaseExecucao filtroSubFaseExecucao);
	List<ResultadoAvaliacaoDTO> listarResultadosAvaliacoes(FiltroResultadoSubFase filtro);
	boolean excluirPorIdSubFaseExecucao(Long idSubFaseExecucao);
	Long recuperarIdSeExistir(Long idCredito, Long idSubFaseExecucao);

}