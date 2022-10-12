package br.com.kronos.backend.aplicacao.matricula.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.matricula.FiltroCredito;


public interface CreditoServico {
	
	List<CreditoContratoDTO> gerarCreditosPendentes(CreditoContratoDTO dto);
	List<CreditoContratoDTO> gerarCreditos(CreditoContratoDTO dto);
	Long criar(CreditoDTO creditoDTO);
	Long alterar(CreditoDTO creditoDTO);
	CreditoDTO buscarPorId(long id);
	PaginacaoDTO<CreditoDTO>listar(FiltroCredito filtroCredito);
	boolean excluir(Long id);
	List<CreditoContratoDTO> listarParaContrato(FiltroCredito filtroCredito);
	
}