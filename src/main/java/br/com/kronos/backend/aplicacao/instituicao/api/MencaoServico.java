package br.com.kronos.backend.aplicacao.instituicao.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.instituicao.FiltroMencao;

public interface MencaoServico {
	
	Long criar(MencaoDTO mencaoDTO) throws ExcecaoDeNegocio;
	Long alterar(MencaoDTO mencaoDTO)throws ExcecaoDeNegocio;
	MencaoDTO buscarPorId(long id);
	PaginacaoDTO<MencaoDTO>listar(FiltroMencao filtroMencao) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	List<MencaoDTO> listarParaCombo(FiltroMencao filtroMencao) throws ExcecaoDeNegocio;

}