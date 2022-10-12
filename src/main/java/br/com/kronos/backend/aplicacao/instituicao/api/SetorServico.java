package br.com.kronos.backend.aplicacao.instituicao.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.instituicao.FiltroSetor;

public interface SetorServico {
	
	Long criar(SetorDTO setorDTO) throws ExcecaoDeNegocio;
	Long alterar(SetorDTO setorDTO)throws ExcecaoDeNegocio;
	SetorDTO buscarPorId(long id);
	PaginacaoDTO<SetorDTO>listar(FiltroSetor filtroSetor) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;

}
