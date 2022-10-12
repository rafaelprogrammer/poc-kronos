package br.com.kronos.backend.aplicacao.basecurricular;

import java.util.List;

public interface NivelRepositorio {
	
	Integer criar(Nivel nivel);
	Integer alterar(Nivel nivel);
	Nivel buscarPorId(Integer id);
	List<Nivel> listar(FiltroNivel filtroNivel);
	boolean excluir(Integer id);
	int contar(FiltroNivel filtroNivel);

}