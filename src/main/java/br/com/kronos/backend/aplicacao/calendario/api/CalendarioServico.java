package br.com.kronos.backend.aplicacao.calendario.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.calendario.FiltroCalendario;
import br.com.kronos.backend.aplicacao.calendario.FiltroCalendarioCurso;


public interface CalendarioServico {
	
	Long criar(CalendarioDTO calendarioDTO);
	Long alterar(CalendarioDTO calendarioDTO);
	CalendarioDTO buscarPorId(long id);
	PaginacaoDTO<CalendarioDTO>listar(FiltroCalendario filtroCalendario);
	boolean excluir(Long id);
	public List<Integer> listarAnos();
	public List<Integer> listarNumerosPorAno(Integer ano);
	void concluir(Long id, Long idResponsavelElaboracao);
	void aprovar(Long id, Long idResponsavelAprovacao);
	void liberarEdicao(Long id, Long idResponsavelElaboracao);
	void verificarSeExistePeriodoExecucao(Long id);
	List<Integer> listarAnos(FiltroCalendarioCurso filtroCalendarioCurso);
	public List<CalendarioResumidoDTO> listarParaCombo(FiltroCalendarioCurso filtroCalendarioCurso);
	
}

