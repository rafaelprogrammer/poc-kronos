package br.com.kronos.backend.aplicacao.conselho.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.conselho.FiltroConselho;
import br.com.kronos.backend.aplicacao.conselho.api.ConselhoDTO;


public interface ConselhoServico {
	
	Long criar(ConselhoDTO conselhoDTO) throws ExcecaoDeNegocio;
	Long alterar(ConselhoDTO conselhoDTO)throws ExcecaoDeNegocio;
	ConselhoDTO buscarPorId(long id);
	PaginacaoDTO<ConselhoDTO>listar(FiltroConselho filtroConselho) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	
}