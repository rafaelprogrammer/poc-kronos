package br.com.kronos.backend.aplicacao.matricula;

import java.util.List;

public interface TurmaSalaRepositorio {
	
	Long criar(TurmaSala turmaSala);
	Long alterar(TurmaSala turmaSala);
	TurmaSala buscarPorId(Long id);
	List<TurmaSala> listar(FiltroTurmaSala filtroTurmaSala);
	boolean excluir(Long id);
	int contar(FiltroTurmaSala filtroTurmaSala);

}