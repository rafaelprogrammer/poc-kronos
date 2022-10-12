package br.com.kronos.backend.aplicacao.calendario.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.calendario.FiltroPeriodoExecucao;
import br.com.kronos.backend.aplicacao.curso.api.EstruturaDTO;


public interface PeriodoExecucaoServico {
	
	void criar(PeriodoExecucaoDTO periodoExecucaoDTO);
	Long alterar(PeriodoExecucaoDTO periodoExecucaoDTO);
	PeriodoExecucaoDTO buscarPorId(long id);
	PaginacaoDTO<PeriodoExecucaoDTO>listar(FiltroPeriodoExecucao filtroPeriodoExecucao);
	boolean excluir(Long id);
	public PaginacaoDTO<PeriodoExecucaoEstruturaAnoLetivoDTO> listarParaEstruturaAnoLetivo(FiltroPeriodoExecucao filtroPeriodoExecucao);
	public PeriodoExecucaoEstruturaAnoLetivoDTO buscarPorIdParaEstruturaAnoLetivo(Long id);
	List<EstruturaDTO> visualizarEstrutura(FiltroPeriodoExecucao filtroPeriodoExecucao);
	public List<PeriodoExecucaoResumoDTO> listarParaModulosDosProfessores(Long idCurso, Long idPessoaUsuarioLogado);
	
	
}
