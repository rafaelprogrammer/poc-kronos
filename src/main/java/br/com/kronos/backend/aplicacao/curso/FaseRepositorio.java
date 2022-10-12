package br.com.kronos.backend.aplicacao.curso;

import java.util.List;

import br.com.kronos.backend.aplicacao.curso.api.FaseResumoDTO;

public interface FaseRepositorio {
	
	Long criar(Fase fase);
	Long alterar(Fase fase);
	Fase buscarPorId(Long id);
	List<Fase> listar(FiltroFase filtroFase);
	List<Fase> listarParaCombo(FiltroFase filtroFase);
	boolean excluir(Long id);
	int contar(FiltroFase filtroFase);
	List<FaseResumoDTO> listarParaGeracaoFaseExecucao(FiltroFase filtroFase);

}