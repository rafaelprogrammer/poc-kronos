package br.com.kronos.backend.aplicacao.curso;

import java.util.List;

import br.com.kronos.backend.aplicacao.curso.api.SubFaseResumoDTO;

public interface SubFaseRepositorio {
	
	Long criar(SubFase subFase);
	Long alterar(SubFase subFase);
	SubFase buscarPorId(Long id);
	List<SubFase> listar(FiltroSubFase filtroSubFase);
	boolean excluir(Long id);
	int contar(FiltroSubFase filtroSubFase);
	List<SubFaseResumoDTO> listarParaGeracaoSubFaseExecucao(FiltroFase filtroFase);
	List<SubFaseResumoDTO> listarParaCombo(FiltroFase filtroFase);
}