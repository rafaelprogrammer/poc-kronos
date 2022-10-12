package br.com.kronos.backend.aplicacao.curso.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.curso.FiltroPeriodo;

public interface PeriodoServico {
	
	Long criar(PeriodoDTO periodoDTO);
	Long alterar(PeriodoDTO periodoDTO);
	PaginacaoDTO<PeriodoDTO>listar(FiltroPeriodo filtroPeriodo);
	List<PeriodoDTO> listarParaCombo(FiltroPeriodo filtroPeriodo);
	PeriodoDTO buscarPorId(long id);
	boolean excluir(Long id);
	List<PeriodoResumoDTO> listarParaComboContrato(FiltroPeriodo filtroPeriodo);
	List<PeriodoResumoDTO> listarParaComboPeriodoPendenteContrato(FiltroPeriodo filtroPeriodo);
	List<PeriodoResumoDTO> listarParaHorario(FiltroPeriodo filtroPeriodo);

}