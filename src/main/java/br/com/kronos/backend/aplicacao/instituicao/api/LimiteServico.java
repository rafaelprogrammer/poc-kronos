package br.com.kronos.backend.aplicacao.instituicao.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.instituicao.FiltroLimite;

public interface LimiteServico {
	
	Long criar(LimiteDTO limiteDTO) throws ExcecaoDeNegocio;
	Long alterar(LimiteDTO limiteDTO)throws ExcecaoDeNegocio;
	LimiteDTO buscarPorId(long id);
	PaginacaoDTO<LimiteDTO>listar(FiltroLimite filtroLimite) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;

}
