package br.com.kronos.backend.aplicacao.avaliacao;

import java.util.List;

import br.com.kronos.backend.aplicacao.avaliacao.api.AvaliacaoDTO;

public interface AvaliacaoRepositorio {
	
	Long criar(Avaliacao avaliacao);
	Long alterar(Avaliacao avaliacao);
	AvaliacaoDTO buscarPorId(Long id);
	List<AvaliacaoDTO> listar(FiltroAvaliacao filtroAvaliacao);
	boolean excluir(Long id);
	int contar(FiltroAvaliacao filtroAvaliacao);
	void criarAvaliacaoAutomatica(Long idAtividadeSelecionada, Long idTurmaSelecionada, Long idDisciplinaSelecionad);

}