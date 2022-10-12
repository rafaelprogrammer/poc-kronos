package br.com.kronos.backend.aplicacao.instituicao;

import java.util.List;

public interface SalaRepositorio {
	
	Long criar(Sala sala);
	Long alterar(Sala sala);
	Sala buscarPorId(Long id);
	List<Sala> listar(FiltroSala filtroSala);
	boolean excluir(Long id);
	int contar(FiltroSala filtroSala);
}