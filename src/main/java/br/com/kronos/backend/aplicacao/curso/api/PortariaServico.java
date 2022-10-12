package br.com.kronos.backend.aplicacao.curso.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.curso.FiltroPortaria; 

public interface PortariaServico {
	
	Long criar(PortariaDTO portariaDTO) throws ExcecaoDeNegocio;
	Long alterar(PortariaDTO portariaDTO) throws ExcecaoDeNegocio;
	PaginacaoDTO<PortariaDTO>listar(FiltroPortaria filtroPortaria) throws ExcecaoDeNegocio;
	PortariaDTO buscarPorId(long id);
	boolean excluir(Long id) throws ExcecaoDeNegocio;

}