package br.com.kronos.backend.aplicacao.instituicao;

import java.util.List;

public interface MencaoRepositorio {
	
	Long criar(Mencao mencao);
	Long alterar(Mencao mencao);
	Mencao buscarPorId(Long id);
	List<Mencao> listar(FiltroMencao filtroMencao);
	boolean excluir(Long id);
	int contar(FiltroMencao filtroMencao);

}