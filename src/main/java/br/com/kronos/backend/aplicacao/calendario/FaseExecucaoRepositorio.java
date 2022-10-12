package br.com.kronos.backend.aplicacao.calendario;

import java.util.List;

import br.com.kronos.backend.aplicacao.calendario.api.DadosCriaFaseExecucaoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.FaseExecucaoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.FaseExecucaoResumoDTO;

public interface FaseExecucaoRepositorio {
	
	Long criar(FaseExecucao faseExecucao);
	Long alterar(FaseExecucao faseExecucao);
	FaseExecucaoDTO buscarPorId(Long id);
	List<FaseExecucaoDTO> listar(FiltroFaseExecucao filtroFaseExecucao);
	boolean excluir(Long id);
	int contar(FiltroFaseExecucao filtroFaseExecucao);
	List<DadosCriaFaseExecucaoDTO> listarParaCriar(FiltroFaseExecucao filtroFaseExecucao);
	FaseExecucao buscarPorIdFaseEAno(Long idFase, Integer ano);
	List<FaseExecucaoResumoDTO> listaParaCombo(Long idPeriodoExecucao);

}