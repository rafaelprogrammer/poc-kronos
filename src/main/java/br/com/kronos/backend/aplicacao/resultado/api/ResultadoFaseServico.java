package br.com.kronos.backend.aplicacao.resultado.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.resultado.FiltroResultadoFase;
import br.com.kronos.backend.aplicacao.resultado.api.ResultadoFaseDTO;


public interface ResultadoFaseServico {
	
	Long criar(ResultadoFaseDTO resultadoFaseDTO) throws ExcecaoDeNegocio;
	Long alterar(ResultadoFaseDTO resultadoFaseDTO)throws ExcecaoDeNegocio;
	ResultadoFaseDTO buscarPorId(long id);
	PaginacaoDTO<ResultadoFaseDTO>listar(FiltroResultadoFase filtroResultadoFase) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	
}

