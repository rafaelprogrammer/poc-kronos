package br.com.kronos.backend.aplicacao.calendario;

import java.util.List;

import br.com.kronos.backend.aplicacao.calendario.api.CalendarioResumidoDTO;

public interface CalendarioCursoRepositorio {
    
    int contarPorIdCalendarioECurso(FiltroCalendarioCurso filtroCalendarioCurso);
	int criar(CalendarioCurso calendarioCurso);
	List<CalendarioCurso> listar(FiltroCalendarioCurso filtroCalendarioCurso);
	int contar(FiltroCalendarioCurso filtroCalendarioCurso);
	boolean excluir(long idCalendario, long idCurso);
	boolean excluirTodosDoCalendario(long idCalendario);
	List<Integer> listarAnos(FiltroCalendarioCurso filtroCalendarioCurso);
	List<CalendarioResumidoDTO> listarParaCombo(FiltroCalendarioCurso filtroCalendarioCurso);

}

