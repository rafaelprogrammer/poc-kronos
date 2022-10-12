package br.com.kronos.backend.aplicacao.avaliacao.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.avaliacao.FiltroAvaliacaoHabilidade;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;


public interface AvaliacaoHabilidadeServico {
	
	Long criar(AvaliacaoHabilidadeDTO avaliacaoHabilidadeDTO) throws ExcecaoDeNegocio;
	Long alterar(AvaliacaoHabilidadeDTO avaliacaoHabilidadeDTO)throws ExcecaoDeNegocio;
	AvaliacaoHabilidadeDTO buscarPorId(long id);
	List<AvaliacaoHabilidadeDTO>listar(FiltroAvaliacaoHabilidade filtroAvaliacaoHabilidade) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	void criarAvaliacoesHabilidades(List<AvaliacaoHabilidadeDTO> dtos) throws ExcecaoDeNegocio;
	
}
