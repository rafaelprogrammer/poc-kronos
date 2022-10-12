package br.com.kronos.backend.aplicacao.parecer.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.parecer.FiltroParecer;
import br.com.kronos.backend.aplicacao.parecer.api.ParecerDTO;


public interface ParecerServico {
	
	Long criar(ParecerDTO parecerDTO) throws ExcecaoDeNegocio;
	Long alterar(ParecerDTO parecerDTO)throws ExcecaoDeNegocio;
	ParecerDTO buscarPorId(long id);
	PaginacaoDTO<ParecerDTO>listar(FiltroParecer filtroParecer) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	
}
