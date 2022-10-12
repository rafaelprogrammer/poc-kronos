package br.com.kronos.backend.aplicacao.calendario;

import java.util.List;

import br.com.kronos.backend.aplicacao.calendario.api.PeriodoExecucaoEstruturaAnoLetivoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.PeriodoExecucaoResumoDTO;
import br.com.kronos.backend.aplicacao.curso.Estrutura;

public interface PeriodoExecucaoRepositorio {
	
	Long criar(PeriodoExecucao periodoExecucao);
	Long alterar(PeriodoExecucao periodoExecucao);
	PeriodoExecucao buscarPorId(Long id);
	List<PeriodoExecucao> listar(FiltroPeriodoExecucao filtroPeriodoExecucao);
	boolean excluir(Long id);
	int contar(FiltroPeriodoExecucao filtroPeriodoExecucao);
	List<PeriodoExecucaoEstruturaAnoLetivoDTO> listarParaEstruturaAnoLetivo(
			FiltroPeriodoExecucao filtroPeriodoExecucao);
	int contarParaEstruturaAnoLetivo(FiltroPeriodoExecucao filtroPeriodoExecucao);
	PeriodoExecucaoEstruturaAnoLetivoDTO buscarPorIdParaEstruturaAnoLetivo(Long id);
	PeriodoExecucao buscarPorIdPeriodoEAno(Long idPeriodo, Integer ano);
	List<Estrutura> visualizarEstrutura(FiltroPeriodoExecucao filtroPeriodoExecucao);
	List<PeriodoExecucaoResumoDTO> listarParaModulosDosProfessores(Long idCurso, Long idPessoaUsuarioLogado);

}