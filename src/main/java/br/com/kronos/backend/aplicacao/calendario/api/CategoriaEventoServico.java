package br.com.kronos.backend.aplicacao.calendario.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.calendario.FiltroCategoriaEvento;
import br.com.kronos.backend.aplicacao.calendario.api.CategoriaEventoDTO;


public interface CategoriaEventoServico {
	
	Long criar(CategoriaEventoDTO categoriaEventoDTO) throws ExcecaoDeNegocio;
	Long alterar(CategoriaEventoDTO categoriaEventoDTO)throws ExcecaoDeNegocio;
	CategoriaEventoDTO buscarPorId(long id);
	PaginacaoDTO<CategoriaEventoDTO>listar(FiltroCategoriaEvento filtroCategoriaEvento) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	
}
