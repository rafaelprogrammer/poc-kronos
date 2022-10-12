package br.com.kronos.backend.aplicacao.relatorio.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.SubFaseExecucaoDTO;
import br.com.kronos.backend.aplicacao.comum.FiltroRelatorio;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaResumoDTO;
import br.com.kronos.backend.aplicacao.relatorio.RelatoriosPedagogicosRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class RelatoriosPedagogicosServicoImpl implements RelatoriosPedagogicosServico {
	
	@NonNull
	private RelatoriosPedagogicosRepositorio relatoriosPedagogicosRepositorio;


	@Override
	public List<SubFaseExecucaoDTO> listarBimestres(FiltroRelatorio filtro) {
		return relatoriosPedagogicosRepositorio.listarBimestres(filtro);
	}

	@Override
	public PaginacaoDTO<DisciplinaResumoDTO> listarDisciplinasProfessores(FiltroRelatorio filtro) {
		List<DisciplinaResumoDTO> disciplinas = relatoriosPedagogicosRepositorio.listarDisciplinasProfessores(filtro);
		return PaginacaoDTO.<DisciplinaResumoDTO>builder().dados(disciplinas).total(disciplinas.size()).build();
	}
	
	@Override
	public PaginacaoDTO<DisciplinaResumoDTO> listarDisciplinasProfessoresResultadosBimestrais(FiltroRelatorio filtro) {
		List<DisciplinaResumoDTO> disciplinas = relatoriosPedagogicosRepositorio.listarDisciplinasProfessoresResultadosBimestrais(filtro);
		return PaginacaoDTO.<DisciplinaResumoDTO>builder().dados(disciplinas).total(disciplinas.size()).build();
	}

	@Override
	public Long encerrarDiario(Long idSubFaseExecucao, Long idDisciplina) {
		return relatoriosPedagogicosRepositorio.encerrarDiario(idSubFaseExecucao, idDisciplina);
	}

	@Override
	public boolean reabrirDiario(Long idSubFaseExecucao, Long idDisciplina) {
		return relatoriosPedagogicosRepositorio.reabrirDiario(idSubFaseExecucao, idDisciplina);
	}
	
	@Override
	public boolean atualizarResultadosBimestrais(FiltroRelatorio filtro) {
		return relatoriosPedagogicosRepositorio.atualizarResultadosBimestrais(filtro);
	}

}
