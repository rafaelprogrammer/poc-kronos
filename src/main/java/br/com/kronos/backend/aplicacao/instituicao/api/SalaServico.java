package br.com.kronos.backend.aplicacao.instituicao.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.instituicao.FiltroSala;

public interface SalaServico {
	
	Long criar(SalaDTO salaDTO) throws ExcecaoDeNegocio;
	Long alterar(SalaDTO salaDTO)throws ExcecaoDeNegocio;
	SalaDTO buscarPorId(long id);
	PaginacaoDTO<SalaDTO>listar(FiltroSala filtroSala) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;

}