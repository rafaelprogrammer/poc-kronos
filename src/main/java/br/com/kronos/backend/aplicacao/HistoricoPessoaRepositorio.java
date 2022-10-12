package br.com.kronos.backend.aplicacao;

import java.util.List;

public interface HistoricoPessoaRepositorio {
	
	Long criar(HistoricoPessoa filiacao);
	Long alterar(HistoricoPessoa filiacao);
	HistoricoPessoa buscarPorId(Long id);
	List<HistoricoPessoa> listar(FiltroHistoricoPessoa filtroHistoricoPessoa);
	boolean excluir(Long id);

}