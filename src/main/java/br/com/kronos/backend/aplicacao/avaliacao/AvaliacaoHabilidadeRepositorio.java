package br.com.kronos.backend.aplicacao.avaliacao;

import java.util.List;

import br.com.kronos.backend.aplicacao.avaliacao.FiltroAvaliacaoHabilidade;
import br.com.kronos.backend.aplicacao.avaliacao.AvaliacaoHabilidade;
import br.com.kronos.backend.aplicacao.avaliacao.api.AvaliacaoHabilidadeDTO;

public interface AvaliacaoHabilidadeRepositorio {
	
	Long criar(AvaliacaoHabilidade avaliacaoHabilidade);
	Long alterar(AvaliacaoHabilidade avaliacaoHabilidade);
	AvaliacaoHabilidadeDTO buscarPorId(Long id);
	List<AvaliacaoHabilidadeDTO> listar(FiltroAvaliacaoHabilidade filtroAvaliacaoHabilidade);
	boolean excluir(Long id);
	int contar(FiltroAvaliacaoHabilidade filtroAvaliacaoHabilidade);
	boolean excluirPorAvaliacao(Long idAvaliacao);
	void criarAvaliacaoHabilidadeAutomatica(Long idAtividadeSelecionada);

}