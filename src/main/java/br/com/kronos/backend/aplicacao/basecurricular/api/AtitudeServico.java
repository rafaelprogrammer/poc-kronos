package br.com.kronos.backend.aplicacao.basecurricular.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroAtitude;

public interface AtitudeServico {
	
	Long criar(AtitudeDTO setorDTO) throws ExcecaoDeNegocio;
	Long alterar(AtitudeDTO setorDTO)throws ExcecaoDeNegocio;
	AtitudeDTO buscarPorId(long id);
	PaginacaoDTO<AtitudeDTO>listar(FiltroAtitude filtroAtitude) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;

}
