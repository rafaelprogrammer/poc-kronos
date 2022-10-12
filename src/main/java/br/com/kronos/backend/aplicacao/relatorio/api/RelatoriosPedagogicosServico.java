package br.com.kronos.backend.aplicacao.relatorio.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.SubFaseExecucaoDTO;
import br.com.kronos.backend.aplicacao.comum.FiltroRelatorio;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaResumoDTO;

public interface RelatoriosPedagogicosServico {

	List<SubFaseExecucaoDTO> listarBimestres(FiltroRelatorio filtro);

	PaginacaoDTO<DisciplinaResumoDTO> listarDisciplinasProfessores(FiltroRelatorio filtro);

	Long encerrarDiario(Long idSubFaseExecucao, Long idDisciplina);

	boolean reabrirDiario(Long idSubFaseExecucao, Long idDisciplina);

	PaginacaoDTO<DisciplinaResumoDTO> listarDisciplinasProfessoresResultadosBimestrais(FiltroRelatorio filtro);

	boolean atualizarResultadosBimestrais(FiltroRelatorio filtro);
	
}
