package br.com.kronos.backend.aplicacao.basecurricular;

import java.util.List;

import br.com.kronos.backend.aplicacao.basecurricular.api.ObjetivoDTO;

public interface ObjetivoRepositorio {
	
	Long criar(Objetivo objetivo);
	Long alterar(Objetivo objetivo);
	ObjetivoDTO buscarPorId(Long id);
	List<ObjetivoDTO> listar(FiltroObjetivo filtroObjetivo);
	boolean excluir(Long id);
	int contar(FiltroObjetivo filtroObjetivo);
	List<ObjetivoDTO> listarParaDisciplinaObjetivo(FiltroObjetivo filtroObjetivo);
	int contarParaDisciplinaObjetivo(FiltroObjetivo filtroObjetivo);

}