package br.com.kronos.backend.aplicacao.instituicao;

import java.util.List;

import br.com.kronos.backend.aplicacao.instituicao.api.ConvenioContratoDTO;

public interface ConvenioRepositorio {
	
	Long criar(Convenio convenio);
	Long alterar(Convenio convenio);
	Convenio buscarPorId(Long id);
	List<Convenio> listar(FiltroConvenio filtroConvenio);
	boolean excluir(Long id);
	int contar(FiltroConvenio filtroConvenio);
	List<ConvenioContratoDTO> listarParaContrato(FiltroConvenio filtroConvenio);
}