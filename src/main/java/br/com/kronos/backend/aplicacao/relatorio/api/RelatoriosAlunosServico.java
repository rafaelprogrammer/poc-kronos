package br.com.kronos.backend.aplicacao.relatorio.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.comum.FiltroRelatorio;

public interface RelatoriosAlunosServico {
	
	PaginacaoDTO<AlunoRelatorioDTO> listarPorTurmaR(FiltroRelatorio filtro);

	PaginacaoDTO<AlunoRelatorioDTO> listarPorPeriodoR(FiltroRelatorio filtro);
	
}
