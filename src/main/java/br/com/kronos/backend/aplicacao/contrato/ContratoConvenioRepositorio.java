package br.com.kronos.backend.aplicacao.contrato;

import java.util.List;

public interface ContratoConvenioRepositorio {
    
    int contarPorIdContratoEIdConvenio(FiltroContratoConvenio filtroContratoConvenio);
	int criar(ContratoConvenio contratoConvenio);
	List<ContratoConvenio> listar(FiltroContratoConvenio filtroContratoConvenio);
	int contar(FiltroContratoConvenio filtroContratoConvenio);
	boolean excluir(long idContrato, long idConvenio);
	Double somarPercentualDesconto(Long idContrato);

}
