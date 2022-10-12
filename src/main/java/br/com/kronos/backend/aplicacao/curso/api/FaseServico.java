package br.com.kronos.backend.aplicacao.curso.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.curso.FiltroFase;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;

public interface FaseServico {
	
	Long criar(FaseDTO faseDTO) throws ExcecaoDeNegocio;
	Long alterar(FaseDTO faseDTO);
	PaginacaoDTO<FaseDTO>listar(FiltroFase filtroFase);
	List<FaseDTO>listarParaCombo(FiltroFase filtroFase);
	FaseDTO buscarPorId(long id);
	boolean excluir(Long id) throws ExcecaoDeNegocio;

}