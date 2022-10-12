package br.com.kronos.backend.aplicacao.contrato;

import java.util.List;

import br.com.kronos.backend.aplicacao.contrato.api.ContratoDTO;

public interface ContratoRepositorio {
	
	Long criar(Contrato contrato);
	Long alterar(Contrato contrato);
	ContratoDTO buscarPorId(Long id);
	List<ContratoDTO> listar(FiltroContrato filtroContrato);
	boolean excluir(Long id);
	int contar(FiltroContrato filtroContrato);
	void alterarSituacaoContrato(Long id, EnumTipoSituacaoContrato tipo);

}