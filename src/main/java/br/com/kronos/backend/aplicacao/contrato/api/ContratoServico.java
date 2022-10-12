package br.com.kronos.backend.aplicacao.contrato.api;

import java.time.LocalDate;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.contrato.FiltroContrato;

public interface ContratoServico {
	
	ContratoDTO buscarPorId(long id);
	PaginacaoDTO<ContratoDTO> listar(FiltroContrato filtroContrato);
	boolean excluir(long id);
	Long criar(ContratoDTO contratoDTO);
	Long alterar(ContratoDTO contratoDTO);
	void validar(Long id);
	void enviarFinanceiro(Long id);
	DiaParcelaDTO calcularDiaVencimento(Integer numeroParcelas, LocalDate dataPrimeiraParcela);
	ContratoDTO buscarContratoMatriculaPorId(long id);
	void atualizarContratoAnalise(ContratoDTO dto);
	void finalizarAnalise(Long id);
	void aprovar(Long id);
	void confirmarAssinatura(Long id);
	void reativar(Long id);
}
