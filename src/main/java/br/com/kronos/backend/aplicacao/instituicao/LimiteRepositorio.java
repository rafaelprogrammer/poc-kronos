package br.com.kronos.backend.aplicacao.instituicao;

import java.util.List;

public interface LimiteRepositorio {
	
	Long criar(Limite limite);
	Long alterar(Limite limite);
	Limite buscarPorId(Long id);
	List<Limite> listar(FiltroLimite filtroLimite);
	boolean excluir(Long id);
	int contar(FiltroLimite filtroLimite);

}