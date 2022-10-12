package br.com.kronos.backend.aplicacao.frequencia.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.frequencia.FiltroFrequencia;


public interface FrequenciaServico {
	
	void alterarArrayFrequencia(FrequenciaDTO frequenciaDTO);
	FrequenciaDTO buscarPorId(long id);
	List<FrequenciaDTO>listar(FiltroFrequencia filtroFrequencia);
	boolean excluir(Long id);
	void criarTipoTP(FrequenciaDTO frequenciaDTO);
	void criarTipoTF(FrequenciaDTO frequenciaDTO);
	void limpar(FrequenciaDTO frequenciaDTO);
	void registrarReposicao(FrequenciaDTO frequenciaDTO);
	void anularReposicao(FrequenciaDTO frequenciaDTO);
	void criarTipoAD(FrequenciaDTO frequenciaDTO);
	void criarTipoTPTodos(FiltroFrequencia filtroFrequencia);
	void criarTipoTFTodos(FiltroFrequencia filtroFrequencia);
	void criarTipoADTodos(FiltroFrequencia filtroFrequencia);
	void limparTodos(FiltroFrequencia filtroFrequencia);
	void criarTiposFrequenciasAtestados(FiltroFrequencia filtroFrequencia);
	
}
