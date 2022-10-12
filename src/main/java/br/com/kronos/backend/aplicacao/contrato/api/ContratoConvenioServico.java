package br.com.kronos.backend.aplicacao.contrato.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.contrato.FiltroContratoConvenio;

public interface ContratoConvenioServico {
	
	void criar(List<ContratoConvenioDTO> dtos);
	PaginacaoDTO<ContratoConvenioDTO> listar(FiltroContratoConvenio filtroContratoConvenio);
	boolean excluir(long idContrato, long idConvenio);
	Double somarPercentualDesconto(Long idContrato);

}