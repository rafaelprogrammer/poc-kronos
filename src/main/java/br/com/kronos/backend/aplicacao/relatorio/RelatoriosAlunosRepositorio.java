package br.com.kronos.backend.aplicacao.relatorio;

import java.util.List;

import br.com.kronos.backend.aplicacao.comum.FiltroRelatorio;
import br.com.kronos.backend.aplicacao.relatorio.api.AlunoRelatorioDTO;

public interface RelatoriosAlunosRepositorio {
	
	List<AlunoRelatorioDTO> listarPorTurmaR(FiltroRelatorio filtro);

	int contarPorTurmaR(FiltroRelatorio filtro);

	List<AlunoRelatorioDTO> listarPorPeriodoR(FiltroRelatorio filtro);

	int contarPorPeriodoR(FiltroRelatorio filtro);
	
}
