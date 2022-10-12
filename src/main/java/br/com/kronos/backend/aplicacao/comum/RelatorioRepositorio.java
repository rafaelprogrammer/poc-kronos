package br.com.kronos.backend.aplicacao.comum;

import java.util.List;
import java.util.Map;

import br.com.kronos.backend.aplicacao.calendario.api.PeriodoExecucaoResumoDTO;
import br.com.kronos.backend.aplicacao.curso.api.CursoResumoDTO;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaResumoDTO;

public interface RelatorioRepositorio {
	
	byte[] exportarParaPdf(EnumRelatorios relatorio, Map<String, Object> parameters);
	
	byte[] exportarParaXLSX(EnumRelatorios relatorio, Map<String, Object> parameters);

	List<Integer> listarAnos();

	List<CursoResumoDTO> listarCursos(FiltroRelatorio filtro);

	List<PeriodoExecucaoResumoDTO> listarPeriodos(FiltroRelatorio filtro);

	List<TurmaResumoDTO> listarTurmas(FiltroRelatorio filtro);


}
