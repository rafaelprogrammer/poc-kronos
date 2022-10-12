package br.com.kronos.backend.aplicacao.frequencia;

import java.util.List;

import br.com.kronos.backend.aplicacao.frequencia.api.FrequenciaDTO;

public interface FrequenciaRepositorio {
	
	Long criar(Frequencia frequencia);
	Long alterar(Frequencia frequencia);
	Frequencia buscarPorId(Long id);
	List<FrequenciaDTO> listar(FiltroFrequencia filtroFrequencia);
	boolean excluir(Long id);
	int contar(FiltroFrequencia filtroFrequencia);
	Long buscarFrequenciaPorIdAtividadeEIdCredito(Long idAtividade, Long idCredito);

}