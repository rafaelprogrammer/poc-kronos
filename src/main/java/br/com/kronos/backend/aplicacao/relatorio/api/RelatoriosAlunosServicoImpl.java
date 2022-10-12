package br.com.kronos.backend.aplicacao.relatorio.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.comum.FiltroRelatorio;
import br.com.kronos.backend.aplicacao.relatorio.RelatoriosAlunosRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class RelatoriosAlunosServicoImpl implements RelatoriosAlunosServico {
	
	@NonNull
	private RelatoriosAlunosRepositorio relatoriosAlunosRepositorio;


	@Override
	public PaginacaoDTO<AlunoRelatorioDTO> listarPorTurmaR(FiltroRelatorio filtro) {
		return PaginacaoDTO.<AlunoRelatorioDTO>builder()
				.dados(relatoriosAlunosRepositorio.listarPorTurmaR(filtro))
				.total(relatoriosAlunosRepositorio.contarPorTurmaR(filtro)).build();
	}
	
	@Override
	public PaginacaoDTO<AlunoRelatorioDTO> listarPorPeriodoR(FiltroRelatorio filtro) {
		return PaginacaoDTO.<AlunoRelatorioDTO>builder()
				.dados(relatoriosAlunosRepositorio.listarPorPeriodoR(filtro))
				.total(relatoriosAlunosRepositorio.contarPorPeriodoR(filtro)).build();
	}


}
