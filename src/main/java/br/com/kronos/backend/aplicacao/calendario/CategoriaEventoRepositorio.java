package br.com.kronos.backend.aplicacao.calendario;

import java.util.List;

public interface CategoriaEventoRepositorio {
	
	Long criar(CategoriaEvento categoriaEvento);
	Long alterar(CategoriaEvento categoriaEvento);
	CategoriaEvento buscarPorId(Long id);
	List<CategoriaEvento> listar(FiltroCategoriaEvento filtroCategoriaEvento);
	boolean excluir(Long id);
	int contar(FiltroCategoriaEvento filtroCategoriaEvento);

}