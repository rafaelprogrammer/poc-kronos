package br.com.kronos.backend.aplicacao.matricula.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.matricula.FiltroHistorico;
import br.com.kronos.backend.aplicacao.matricula.api.HistoricoDTO;


public interface HistoricoServico {
	
	Long criar(HistoricoDTO historicoDTO) throws ExcecaoDeNegocio;
	Long alterar(HistoricoDTO historicoDTO)throws ExcecaoDeNegocio;
	HistoricoDTO buscarPorId(long id);
	PaginacaoDTO<HistoricoDTO>listar(FiltroHistorico filtroHistorico) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	
}

