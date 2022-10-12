package br.com.kronos.backend.aplicacao.matricula.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.cache.annotation.Cacheable;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.DadosCriaTurmaDTO;
import br.com.kronos.backend.aplicacao.curso.api.EstruturaDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.matricula.FiltroTurma;
import br.com.kronos.backend.aplicacao.matricula. Turma;
import br.com.kronos.backend.aplicacao.matricula.TurmaRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class  TurmaServicoImpl implements TurmaServico {

	@NonNull
	private  TurmaRepositorio turmaRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public void gerar(TurmaDTO turmaDTO) throws ExcecaoDeNegocio {
		List<DadosCriaTurmaDTO> dadosCriaTurmaDTOs = turmaRepositorio.listarParaGeracaoDeTurmas(FiltroTurma.builder()
																									.idInstituicao(turmaDTO.getIdInstituicao())
																									.idCurso(turmaDTO.getIdCurso())
																									.ano(turmaDTO.getAno())
																									.build());
		if(dadosCriaTurmaDTOs != null && !dadosCriaTurmaDTOs.isEmpty()) {
			dadosCriaTurmaDTOs.stream().forEach(dado -> {
				turmaRepositorio.criar(Turma.builder()
                        .ano(turmaDTO.getAno())
                        .sigla(dado.getSigla())
                        .ativa(true)
                        .aberta(true)
                        .encerrada(false)
                        .idTipoTurno(dado.getIdTipoTurno())
                        .idPeriodoExecucao(dado.getIdPeriodoExecucao())
                        .idCalendario(turmaDTO.getIdCalendario()).build());
			});
		}

    }
	
	@Override
	public Long criar(TurmaDTO turmaDTO) {
			return turmaRepositorio.criar( Turma.builder()
												.ano(turmaDTO.getAno())                                
	                                            .sigla(turmaDTO.getSigla())
	                                            .ativa(turmaDTO.isAtiva())
	                                            .aberta(turmaDTO.isAberta())
	                                            .encerrada(turmaDTO.isEncerrada())
	                                            .idTipoTurno(turmaDTO.getIdTipoTurno())
	                                            .idPeriodoExecucao(turmaDTO.getIdPeriodoExecucao())
	                                            .idCalendario(turmaDTO.getIdCalendario()).build());	
		
	}
	
	@Override
	public Long alterar(TurmaDTO turmaDTO) throws ExcecaoDeNegocio {
			return turmaRepositorio.alterar( Turma.builder()
												.id(turmaDTO.getId())
												.ano(turmaDTO.getAno())                                
	                                            .sigla(turmaDTO.getSigla())
	                                            .ativa(turmaDTO.isAtiva())
	                                            .aberta(turmaDTO.isAberta())
	                                            .encerrada(turmaDTO.isEncerrada())
	                                            .idTipoTurno(turmaDTO.getIdTipoTurno())
	                                            .idPeriodoExecucao(turmaDTO.getIdPeriodoExecucao())
	                                            .idCalendario(turmaDTO.getIdCalendario()).build());	
		
	}

	@Override
	public  TurmaDTO buscarPorId (long id) {
		return turmaRepositorio.buscarPorId(id);
	}

	@Override
	public PaginacaoDTO<TurmaDTO> listar(FiltroTurma filtroTurma) throws ExcecaoDeNegocio {
		int total = turmaRepositorio.contar(filtroTurma);
		return PaginacaoDTO.< TurmaDTO>builder().total(total).dados(turmaRepositorio.listar(filtroTurma)).build();
	}

	@Override
	public boolean excluir(Long id) {
		return turmaRepositorio.excluir(id);
	}

	@Override
	public List<TurmaResumoDTO> listarParaTurmaContratoCombo(Long idPeriodoExecucao) {
		return turmaRepositorio.listarParaTurmaContratoCombo(idPeriodoExecucao);
	}

	@Override
	public List<Integer> listarAnosParaHorario() {
		return turmaRepositorio.listarAnosParaHorario();
	}

	@Override
	public List<TurmaResumoDTO> listarParaHorario(Long idPeriodoExecucao) {
		return turmaRepositorio.listarParaHorario(idPeriodoExecucao);
	}

	@Override
	public List<EstruturaDTO> visualizarEstrutura(FiltroTurma filtro) {
		return modelMapper.map(turmaRepositorio.visualizarEstrutura(filtro),
				new TypeToken<List<EstruturaDTO>>() {
				}.getType());
	}

	@Override
	@Cacheable(cacheNames = Turma.COMBO_CACHE_NAME_PERFIL_PROFESSORES, key="{#idPeriodoExecucao, #idPessoaUsuarioLogado}")
	public List<TurmaResumoDTO> listarParaModulosDosProfessores(Long idPeriodoExecucao, Long idPessoaUsuarioLogado) {
		return turmaRepositorio.listarParaModulosDosProfessores(idPeriodoExecucao, idPessoaUsuarioLogado);
	}

	@Override
	@Cacheable(cacheNames = Turma.COMBO_CACHE_NAME_ANOS_POR_CURSO, key="{#idCurso}")
	public List<Integer> listarAnosFiltroIdCurso(Long idCurso) {
		return turmaRepositorio.listarAnosFiltroIdCurso(idCurso);
	}

	@Override
	@Cacheable(cacheNames = Turma.COMBO_CACHE_NAME_POR_CURSO_E_ANO, key="{#idCurso, #ano}")
	public List<TurmaResumoDTO> listarPorIdCursoEAno(Long idCurso, Integer ano) {
		return turmaRepositorio.listarPorIdCursoEAno(idCurso, ano);
	}

	@Override
	@Cacheable(cacheNames = Turma.COMBO_CACHE_NAME_OCORRENCIAS_POR_MATRICULA, key="{#idMatriculaSelecionada}")
	public List<TurmaResumoDTO> listarParaOcorrenciaCombo(Long idMatriculaSelecionada) {
		return turmaRepositorio.listarParaOcorrenciaCombo(idMatriculaSelecionada);
	}
	
	@Override
	@Cacheable(cacheNames = Turma.COMBO_CACHE_DIARIO_FREQUENCIA, key="{#idInstituicaoUsuarioLogado, #idPessoaUsuarioLogado}")
	public List<TurmaDiarioFrequenciaDTO> listarParaDiarioFrequencia(Long idInstituicaoUsuarioLogado, Long idPessoaUsuarioLogado) {
		return turmaRepositorio.listarParaDiarioFrequencia(idInstituicaoUsuarioLogado, idPessoaUsuarioLogado);
	}
}
