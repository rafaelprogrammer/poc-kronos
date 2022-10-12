package br.com.kronos.backend.aplicacao.resultado.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.calendario.FiltroSubFaseExecucao;
import br.com.kronos.backend.aplicacao.calendario.api.EstatisticaResultadoSubFaseDTO;
import br.com.kronos.backend.aplicacao.calendario.api.PeriodoExecucaoResumoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.SubFaseExecucaoResumoDTO;
import br.com.kronos.backend.aplicacao.curso.api.CursoResumoDTO;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaResumoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaResumoDTO;
import br.com.kronos.backend.aplicacao.resultado.FiltroResultadoSubFase;
import br.com.kronos.backend.aplicacao.resultado.ResultadoSubFase;
import br.com.kronos.backend.aplicacao.resultado.ResultadoSubFaseRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class ResultadoSubFaseServicoImpl implements ResultadoSubFaseServico {

	@NonNull
	private ResultadoSubFaseRepositorio resultadoSubFaseRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void criar(List<ResultadoSubFaseDTO> resultados) {
		
		if(resultados != null && !resultados.isEmpty()) {
			resultados.forEach(resultadoSubFaseDTO -> {
				Long idResultadoSubFase = resultadoSubFaseRepositorio.recuperarIdSeExistir(resultadoSubFaseDTO.getIdCredito(), resultadoSubFaseDTO.getIdSubFaseExecucao());
				ResultadoSubFase.ResultadoSubFaseBuilder resultadoSubFase = ResultadoSubFase.builder()
						.totalAusencia(resultadoSubFaseDTO.getTotalAusencia())
						.percentualAusencia(resultadoSubFaseDTO.getPercentualAusencia())
						.idSubFaseExecucao(resultadoSubFaseDTO.getIdSubFaseExecucao())
						.idCredito(resultadoSubFaseDTO.getIdCredito())
						.idMencao(resultadoSubFaseDTO.getIdMencao()); 
				if (idResultadoSubFase == null || idResultadoSubFase == 0L) {
					resultadoSubFaseRepositorio
					.criar(resultadoSubFase
							.build());
				} else {
					resultadoSubFaseRepositorio
					.alterar(resultadoSubFase
							.id(idResultadoSubFase)
							.build());
				}
			});
		}

	}

	@Override
	public List<ResultadoSubFaseDTO> listarAlunosResultados(FiltroResultadoSubFase filtroResultadoSubFase)
			throws ExcecaoDeNegocio {

		return resultadoSubFaseRepositorio.listarAlunosResultados(filtroResultadoSubFase);
	}

	@Override
	public boolean excluir(Long id) {
		return resultadoSubFaseRepositorio.excluir(id);
	}

	@Override
	public List<CursoResumoDTO> listarComboCurso(FiltroSubFaseExecucao filtroSubFaseExecucao) {
		return resultadoSubFaseRepositorio.listarComboCurso(filtroSubFaseExecucao);
	}

	@Override
	public List<PeriodoExecucaoResumoDTO> listarComboPeriodoExecucao(FiltroSubFaseExecucao filtroSubFaseExecucao) {
		return resultadoSubFaseRepositorio.listarComboPeriodoExecucao(filtroSubFaseExecucao);
	}

	@Override
	public List<TurmaResumoDTO> listarComboTurma(FiltroSubFaseExecucao filtroSubFaseExecucao) {
		return resultadoSubFaseRepositorio.listarComboTurma(filtroSubFaseExecucao);
	}

	@Override
	public List<DisciplinaResumoDTO> listarComboDisciplina(FiltroSubFaseExecucao filtroSubFaseExecucao) {
		return resultadoSubFaseRepositorio.listarComboDisciplina(filtroSubFaseExecucao);
	}

	@Override
	public List<SubFaseExecucaoResumoDTO> listarComboSubFaseExecucao(FiltroSubFaseExecucao filtroSubFaseExecucao) {
		return resultadoSubFaseRepositorio.listarComboSubFaseExecucao(filtroSubFaseExecucao);
	}

	@Override
	public EstatisticaResultadoSubFaseDTO recuperarEstatistasResultado(FiltroSubFaseExecucao filtroSubFaseExecucao) {
		return EstatisticaResultadoSubFaseDTO.builder()
				.qtdAulasDadas(resultadoSubFaseRepositorio.contarAulasDadas(filtroSubFaseExecucao))
				.nrHabilidadesAvaliadas(resultadoSubFaseRepositorio.contarNrHabilidadesAvaliadas(filtroSubFaseExecucao))
				.nrHabilidadesTrabalhadas(
						resultadoSubFaseRepositorio.contarNrHabilidadesTrabalhadas(filtroSubFaseExecucao))
				.build();
	}

	@Override
	public List<ResultadoAvaliacaoDTO> listarResultadosAvaliacoes(FiltroResultadoSubFase filtro) {
		return resultadoSubFaseRepositorio.listarResultadosAvaliacoes(filtro);
	}

}
