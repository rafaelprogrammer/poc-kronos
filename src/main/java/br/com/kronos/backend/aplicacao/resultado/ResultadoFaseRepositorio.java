package br.com.kronos.backend.aplicacao.resultado;

import java.util.List;

public interface ResultadoFaseRepositorio {
	
	Long criar(ResultadoFase resultadoFase);
	Long alterar(ResultadoFase resultadoFase);
	ResultadoFase buscarPorId(Long id);
	List<ResultadoFase> listar(FiltroResultadoFase filtroResultadoFase);
	boolean excluir(Long id);
	int contar(FiltroResultadoFase filtroResultadoFase);

}