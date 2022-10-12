package br.com.kronos.backend.aplicacao.calendario.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.calendario.FiltroCalendarioCurso;

public interface CalendarioCursoServico {
	
	void criar(List<CalendarioCursoDTO> dtos);
	PaginacaoDTO<CalendarioCursoDTO> listar(FiltroCalendarioCurso filtroCalendarioCurso);
	boolean excluir(long idCalendario, long idCurso);
}

