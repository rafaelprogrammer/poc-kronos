package br.com.kronos.backend.aplicacao.instituicao.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.instituicao.FiltroConvenio;


public interface ConvenioServico {
	
	Long criar(ConvenioDTO convenioDTO);
	Long alterar(ConvenioDTO convenioDTO);
	ConvenioDTO buscarPorId(long id);
	PaginacaoDTO<ConvenioDTO>listar(FiltroConvenio filtroConvenio);
	boolean excluir(Long id);
	List<ConvenioContratoDTO> listarParaContrato(FiltroConvenio filtroConvenio);
	
}
