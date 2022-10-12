package br.com.kronos.backend.aplicacao.curso;

import java.util.List;

import br.com.kronos.backend.aplicacao.curso.api.PeriodoDTO;
import br.com.kronos.backend.aplicacao.curso.api.PeriodoResumoDTO; 

public interface PeriodoRepositorio {
	
	Long criar(Periodo periodo);
	Long alterar(Periodo periodo);
	PeriodoDTO buscarPorId(Long id);
	List<PeriodoDTO> listar(FiltroPeriodo filtroPeriodo);
	List<PeriodoDTO> listarParaCombo(FiltroPeriodo filtroPeriodo);
	List<PeriodoResumoDTO> listarParaComboContrato(FiltroPeriodo filtroPeriodo);
	boolean excluir(Long id);
	int contar(FiltroPeriodo filtroPeriodo);
	List<PeriodoResumoDTO> listarParaComboPeriodoPendenteContrato(FiltroPeriodo filtroPeriodo);
	PeriodoDTO buscarPorIdPeriodoExecucao(Long idPeriodoExecucao);
	List<PeriodoResumoDTO> listarParaHorario(FiltroPeriodo filtroPeriodo);
	List<Long> retornarIdsPeriodoParaGerarPeriodosDeExecucao(FiltroPeriodo filtroPeriodo);

}