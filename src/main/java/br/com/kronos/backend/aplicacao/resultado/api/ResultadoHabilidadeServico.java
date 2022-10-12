package br.com.kronos.backend.aplicacao.resultado.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.resultado.FiltroResultadoHabilidade;
import br.com.kronos.backend.aplicacao.resultado.api.ResultadoHabilidadeDTO;


public interface ResultadoHabilidadeServico {
	
	void criar(ResultadoHabilidadeDTO resultadoHabilidadeDTO) throws ExcecaoDeNegocio;
	Long alterar(ResultadoHabilidadeDTO resultadoHabilidadeDTO)throws ExcecaoDeNegocio;
	ResultadoHabilidadeDTO buscarPorId(long id);
	PaginacaoDTO<ResultadoHabilidadeDTO>listar(FiltroResultadoHabilidade filtroResultadoHabilidade) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	
}
