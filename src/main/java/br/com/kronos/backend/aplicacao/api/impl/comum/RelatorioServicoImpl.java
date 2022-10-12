package br.com.kronos.backend.aplicacao.api.impl.comum;

import java.util.List;
import java.util.Map;

import br.com.kronos.backend.aplicacao.calendario.api.PeriodoExecucaoResumoDTO;
import br.com.kronos.backend.aplicacao.comum.EnumRelatorios;
import br.com.kronos.backend.aplicacao.comum.FiltroRelatorio;
import br.com.kronos.backend.aplicacao.comum.RelatorioRepositorio;
import br.com.kronos.backend.aplicacao.curso.api.CursoResumoDTO;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaResumoDTO;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class RelatorioServicoImpl implements RelatorioServico {
	
	@NonNull
	private RelatorioRepositorio relatorioRepositorio;

	@Override
	public byte[] exportarParaPdf(EnumRelatorios relatorio, Map<String, Object> parameters) {
		return relatorioRepositorio.exportarParaPdf(relatorio, parameters);
	}
	
	@Override
	public byte[] exportarParaXLSX(EnumRelatorios relatorio, Map<String, Object> parameters) {
		return relatorioRepositorio.exportarParaXLSX(relatorio, parameters);
	}

	@Override
	public List<Integer> listarAnos() {
		return relatorioRepositorio.listarAnos();
	}

	@Override
	public List<CursoResumoDTO> listarCursos(FiltroRelatorio filtro) {
		return relatorioRepositorio.listarCursos(filtro);
	}

	@Override
	public List<PeriodoExecucaoResumoDTO> listarPeriodos(FiltroRelatorio filtro) {
		return relatorioRepositorio.listarPeriodos(filtro);
	}

	@Override
	public List<TurmaResumoDTO> listarTurmas(FiltroRelatorio filtro) {
		return relatorioRepositorio.listarTurmas(filtro);
	}


}
