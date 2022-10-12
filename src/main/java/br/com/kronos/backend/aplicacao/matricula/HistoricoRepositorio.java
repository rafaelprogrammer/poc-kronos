package br.com.kronos.backend.aplicacao.matricula;

import java.util.List;

public interface HistoricoRepositorio {
	
	Long criar(Historico historico);
	Long alterar(Historico historico);
	Historico buscarPorId(Long id);
	List<Historico> listar(FiltroHistorico filtroHistorico);
	boolean excluir(Long id);
	int contar(FiltroHistorico filtroHistorico);

}