package br.com.kronos.backend.aplicacao.horario.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.horario.FiltroHoraAtividade;
import br.com.kronos.backend.aplicacao.horario.api.HoraAtividadeDTO;


public interface HoraAtividadeServico {
	
	Long criar(HoraAtividadeDTO horaAtividadeDTO) throws ExcecaoDeNegocio;
	Long alterar(HoraAtividadeDTO horaAtividadeDTO)throws ExcecaoDeNegocio;
	HoraAtividadeDTO buscarPorId(long id);
	PaginacaoDTO<HoraAtividadeDTO>listar(FiltroHoraAtividade filtroHoraAtividade) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	
}
