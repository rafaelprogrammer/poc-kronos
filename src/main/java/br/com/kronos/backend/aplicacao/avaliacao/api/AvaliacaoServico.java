package br.com.kronos.backend.aplicacao.avaliacao.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.avaliacao.FiltroAvaliacao;
import br.com.kronos.backend.aplicacao.avaliacao.api.AvaliacaoDTO;


public interface AvaliacaoServico {
	
	void criar(AvaliacaoDTO avaliacaoDTO) throws ExcecaoDeNegocio;
	void alterar(AvaliacaoDTO avaliacaoDTO)throws ExcecaoDeNegocio;
	AvaliacaoDTO buscarPorId(long id);
	PaginacaoDTO<AvaliacaoDTO>listar(FiltroAvaliacao filtroAvaliacao) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	List<AvaliacaoDTO> listarParaCombo(FiltroAvaliacao build);
	void criarAvaliacaoAutomatica(AvaliacaoDTO avaliacaoDTO);
	
}

