package br.com.kronos.backend.aplicacao.matricula;

import java.util.List;

import br.com.kronos.backend.aplicacao.matricula.api.CreditoContratoDTO;

public interface CreditoRepositorio {
	
	Long criar(Credito credito);
	Long alterar(Credito credito);
	Credito buscarPorId(Long id);
	List<Credito> listar(FiltroCredito filtroCredito);
	boolean excluir(Long id);
	int contar(FiltroCredito filtroCredito);
	List<CreditoContratoDTO> listarParaContrato(FiltroCredito filtroCredito);
	boolean excluirPorContrato(Long idContrato);
	Double somarValores(Long idContrato, Boolean pendencia);

}