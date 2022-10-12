package br.com.kronos.backend.aplicacao.instituicao.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.instituicao.FiltroInstituicao;

public interface InstituicaoServico {
	
	Long criar(InstituicaoDTO convenioDTO) throws ExcecaoDeNegocio;
	Long alterar(InstituicaoDTO convenioDTO)throws ExcecaoDeNegocio;
	InstituicaoDTO buscarPorId(long id);
	PaginacaoDTO<InstituicaoDTO>listar(FiltroInstituicao filtroInstituicao) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;

}
