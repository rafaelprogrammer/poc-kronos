package br.com.kronos.backend.aplicacao.calendario.api;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.calendario.Calendario;
import br.com.kronos.backend.aplicacao.calendario.CalendarioCurso;
import br.com.kronos.backend.aplicacao.calendario.CalendarioCursoRepositorio;
import br.com.kronos.backend.aplicacao.calendario.CalendarioRepositorio;
import br.com.kronos.backend.aplicacao.calendario.EnumTipoSituacaoCalendario;
import br.com.kronos.backend.aplicacao.calendario.EventoRepositorio;
import br.com.kronos.backend.aplicacao.calendario.FiltroCalendario;
import br.com.kronos.backend.aplicacao.calendario.FiltroCalendarioCurso;
import br.com.kronos.backend.aplicacao.calendario.FiltroEvento;
import br.com.kronos.backend.aplicacao.calendario.FiltroPeriodoExecucao;
import br.com.kronos.backend.aplicacao.calendario.PeriodoExecucaoRepositorio;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.funcionario.FuncionarioRepositorio;
import br.com.kronos.backend.aplicacao.funcionario.api.FuncionarioDTO;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class CalendarioServicoImpl implements CalendarioServico {

	@NonNull
	private CalendarioRepositorio calendarioRepositorio;
	
	@NonNull
	private CalendarioCursoRepositorio calendarioCursoRepositorio;
	
	@NonNull
	private FuncionarioRepositorio funcionarioRepositorio;
	
	@NonNull
	private PeriodoExecucaoRepositorio periodoExecucaoRepositorio;
	
	@NonNull
	private EventoRepositorio eventoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long criar(CalendarioDTO calendarioDTO) throws ExcecaoDeNegocio {

		Long idCalendario = calendarioRepositorio.criar(Calendario.builder()
				                        .numero(calendarioDTO.getNumero())                                
                                        .ano(calendarioDTO.getAno())
                                        .dataInicio(calendarioDTO.getDataInicio())
                                        .dataFinal(calendarioDTO.getDataFinal())
                                        .dataInicioAtribuicaoCredito(calendarioDTO.getDataInicioAtribuicaoCredito())
                                        .dataFinalAtribuicaoCredito(calendarioDTO.getDataFinalAtribuicaoCredito())
                                        .dataInicioAnoLetivo(calendarioDTO.getDataInicioAnoLetivo())
                                        .dataFinalAnoLetivo(calendarioDTO.getDataFinalAnoLetivo())
                                        .descricao(calendarioDTO.getDescricao())
                                        .dataAprovacao(calendarioDTO.getDataAprovacao())
                                        .idInstituicao(calendarioDTO.getIdInstituicao())
                                        .idFuncionarioAprovacao(calendarioDTO.getIdFuncionarioAprovacao())
                                        .idTipoSituacaoCalendario(calendarioDTO.getIdTipoSituacaoCalendario())
                                        .dataConclusao(calendarioDTO.getDataConclusao())
                                        .idFuncionarioElaboracao(calendarioDTO.getIdFuncionarioElaboracao()).build());	
		
		registrarCursosDoCalendario(calendarioDTO.getIdsCursos(), idCalendario);
		
		return idCalendario;
    }

	private void registrarCursosDoCalendario(List<Long> idsCursos, Long idCalendario) {
		if(idsCursos != null && !idsCursos.isEmpty()) {
			calendarioCursoRepositorio.excluirTodosDoCalendario(idCalendario);
			idsCursos.stream().forEach(idCurso -> {
				calendarioCursoRepositorio.criar(CalendarioCurso.builder()
					.idCalendario(idCalendario)
					.idCurso(idCurso)
					.build());
			});
		}
	}
		
	@Override
	public Long alterar(CalendarioDTO calendarioDTO) throws ExcecaoDeNegocio {
		calendarioRepositorio.alterar(Calendario.builder()
										.id(calendarioDTO.getId())
										.numero(calendarioDTO.getNumero())                                
                                        .ano(calendarioDTO.getAno())
                                        .dataInicio(calendarioDTO.getDataInicio())
                                        .dataFinal(calendarioDTO.getDataFinal())
                                        .dataInicioAtribuicaoCredito(calendarioDTO.getDataInicioAtribuicaoCredito())
                                        .dataFinalAtribuicaoCredito(calendarioDTO.getDataFinalAtribuicaoCredito())
                                        .dataInicioAnoLetivo(calendarioDTO.getDataInicioAnoLetivo())
                                        .dataFinalAnoLetivo(calendarioDTO.getDataFinalAnoLetivo())
                                        .descricao(calendarioDTO.getDescricao())
                                        .dataAprovacao(calendarioDTO.getDataAprovacao())
                                        .idInstituicao(calendarioDTO.getIdInstituicao())
                                        .idFuncionarioAprovacao(calendarioDTO.getIdFuncionarioAprovacao())
                                        .idTipoSituacaoCalendario(calendarioDTO.getIdTipoSituacaoCalendario())
                                        .dataConclusao(calendarioDTO.getDataConclusao())
                                        .idFuncionarioElaboracao(calendarioDTO.getIdFuncionarioElaboracao()).build());
		
		registrarCursosDoCalendario(calendarioDTO.getIdsCursos(), calendarioDTO.getId());
		
		return calendarioDTO.getId();
	}

	@Override
	public CalendarioDTO buscarPorId (long id) {
		CalendarioDTO dto = calendarioRepositorio.buscarPorId(id);
		List<CalendarioCurso> calendariosCursos = calendarioCursoRepositorio.listar(FiltroCalendarioCurso.builder().idCalendario(id).build());
		if (calendariosCursos != null &&  !calendariosCursos.isEmpty()) {
			dto.setIdsCursos(calendariosCursos.stream().map(cc -> cc.getIdCurso()).collect(Collectors.toList()));
		}
		return dto;
	}

	@Override
	public PaginacaoDTO<CalendarioDTO> listar(FiltroCalendario filtroCalendario) throws ExcecaoDeNegocio {
			int total = calendarioRepositorio.contar(filtroCalendario);
			
			return PaginacaoDTO.<CalendarioDTO>builder().total(total).dados(calendarioRepositorio.listar(filtroCalendario)).build();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean excluir(Long id) {
		calendarioCursoRepositorio.excluirTodosDoCalendario(id);
		eventoRepositorio.excluir(FiltroEvento.builder().idCalendario(id).build());
		return calendarioRepositorio.excluir(id);
	}

	@Override
	public void verificarSeExistePeriodoExecucao(Long id) {
		long qtdPeriodoExecucao = periodoExecucaoRepositorio.contar(FiltroPeriodoExecucao.builder().idCalendario(id).build());
		if (qtdPeriodoExecucao > 0) {
			throw new ExcecaoDeNegocio("Não é possível excluir o calendário, pois existem períodos em execução");
		}
	}

	@Override
	public List<Integer> listarAnos() {
		return calendarioRepositorio.listarAnos();
	}

	@Override
	public List<Integer> listarNumerosPorAno(Integer ano) {
		return calendarioRepositorio.listarNumerosPorAno(ano);
	}

	@Override
	public void concluir(Long id, Long idResponsavelElaboracao) {
		FuncionarioDTO funcionarioDTO = funcionarioRepositorio.buscarPorIdPessoa(idResponsavelElaboracao);
		CalendarioDTO calendarioDTO = calendarioRepositorio.buscarPorId(id);
		if (calendarioDTO.getIdTipoSituacaoCalendario().equals(EnumTipoSituacaoCalendario.EM_ELABORACAO.id())) {
			calendarioRepositorio.alterarSituacao(id, EnumTipoSituacaoCalendario.CONCLUIDO, funcionarioDTO.getId(), null);
		}
	}

	@Override
	public void aprovar(Long id, Long idResponsavelAprovacao) {
		FuncionarioDTO funcionarioDTO = funcionarioRepositorio.buscarPorIdPessoa(idResponsavelAprovacao);
		CalendarioDTO calendarioDTO = calendarioRepositorio.buscarPorId(id);
		if (calendarioDTO.getIdTipoSituacaoCalendario().equals(EnumTipoSituacaoCalendario.CONCLUIDO.id())) {
			calendarioRepositorio.alterarSituacao(id, EnumTipoSituacaoCalendario.APROVADO, null, funcionarioDTO.getId());
		}
		
	}

	@Override
	public void liberarEdicao(Long id, Long idResponsavelElaboracao) {
		FuncionarioDTO funcionarioDTO = funcionarioRepositorio.buscarPorIdPessoa(idResponsavelElaboracao);
		CalendarioDTO calendarioDTO = calendarioRepositorio.buscarPorId(id);
		if (calendarioDTO.getIdTipoSituacaoCalendario().equals(EnumTipoSituacaoCalendario.APROVADO.id())) {
			calendarioRepositorio.alterarSituacao(id, EnumTipoSituacaoCalendario.EM_ELABORACAO, funcionarioDTO.getId(), null);
		}
	}
	
	@Override
	public List<Integer> listarAnos(FiltroCalendarioCurso filtroCalendarioCurso) {
		return calendarioCursoRepositorio.listarAnos(filtroCalendarioCurso);
	}

	@Override
	public List<CalendarioResumidoDTO> listarParaCombo(FiltroCalendarioCurso filtroCalendarioCurso) {
		return calendarioCursoRepositorio.listarParaCombo(filtroCalendarioCurso);
	}

}

