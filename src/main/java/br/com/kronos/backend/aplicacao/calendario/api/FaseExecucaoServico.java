package br.com.kronos.backend.aplicacao.calendario.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.calendario.FiltroFaseExecucao;
import br.com.kronos.backend.aplicacao.curso.FiltroFase;
import br.com.kronos.backend.aplicacao.curso.api.FaseResumoDTO;


public interface FaseExecucaoServico {
	
	void criar(List<FaseExecucaoDTO> dtos);
	Long alterar(FaseExecucaoDTO faseExecucaoDTO);
	FaseExecucaoDTO buscarPorId(long id);
	PaginacaoDTO<FaseExecucaoDTO>listar(FiltroFaseExecucao filtroFaseExecucao);
	boolean excluir(Long id);
	List<FaseResumoDTO> listarParaGeracaoFaseExecucao(FiltroFase filtroFase);
	List<FaseExecucaoResumoDTO> listaParaCombo(Long idPeriodoExecucao);
	
}

