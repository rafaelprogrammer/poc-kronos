package br.com.kronos.backend.aplicacao.basecurricular.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroNivel;

public interface NivelServico {
	
	Integer criar(NivelDTO nivelDTO) throws ExcecaoDeNegocio;
	Integer alterar(NivelDTO nivelDTO)throws ExcecaoDeNegocio;
	NivelDTO buscarPorId(int id);
	PaginacaoDTO<NivelDTO>listar(FiltroNivel filtroNivel) throws ExcecaoDeNegocio;
	boolean excluir(int id) throws ExcecaoDeNegocio;
	List<NivelDTO> listarParaCombo();

}

