package br.com.kronos.backend.aplicacao.diario.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.UsuarioDTO;
import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.calendario.CalendarioRepositorio;
import br.com.kronos.backend.aplicacao.calendario.EventoRepositorio;
import br.com.kronos.backend.aplicacao.calendario.SubFaseExecucaoRepositorio;
import br.com.kronos.backend.aplicacao.comum.ServicoAutenticacao;
import br.com.kronos.backend.aplicacao.diario.Atividade;
import br.com.kronos.backend.aplicacao.diario.AtividadeDisciplinaCompetencia;
import br.com.kronos.backend.aplicacao.diario.AtividadeDisciplinaDireito;
import br.com.kronos.backend.aplicacao.diario.AtividadeDisciplinaHabilidade;
import br.com.kronos.backend.aplicacao.diario.AtividadeDisciplinaObjetivo;
import br.com.kronos.backend.aplicacao.diario.AtividadeRepositorio;
import br.com.kronos.backend.aplicacao.diario.FiltroAtividade;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.funcionario.api.FuncionarioDTO;
import br.com.kronos.backend.aplicacao.funcionario.api.FuncionarioServico;
import br.com.kronos.backend.aplicacao.horario.EnumTipoDiaSemana;
import br.com.kronos.backend.aplicacao.horario.Horario;
import br.com.kronos.backend.aplicacao.horario.HorarioRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class AtividadeServicoImpl implements AtividadeServico {

	@NonNull
	private AtividadeRepositorio atividadeRepositorio;
	
	@NonNull
	private HorarioRepositorio horarioRepositorio; 

	@NonNull
	private SubFaseExecucaoRepositorio subFaseExecucaoRepositorio;
	
	@NonNull
	private CalendarioRepositorio calendarioRepositorio;
	
	@NonNull
	private EventoRepositorio eventoRepositorio;
	
	@NonNull
	private ServicoAutenticacao servicoAutenticacao;
	
	@NonNull
	private FuncionarioServico funcionarioServico;
	
	private void adicionarDadosUsuarioLogado(AtividadeDTO dto, boolean buscaFuncionario) {
		UsuarioDTO usuario = servicoAutenticacao.buscarUsuarioLogado();
		dto.setIdPessoaUsuarioLogado(usuario.getIdPessoa());
		if (buscaFuncionario) {
			FuncionarioDTO funcionario = funcionarioServico.buscarPorIdPessoa(usuario.getIdPessoa());
			dto.setIdFuncionario(funcionario.getId());
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void criar(AtividadeDTO atividadeDTO) throws ExcecaoDeNegocio {
		adicionarDadosUsuarioLogado(atividadeDTO, false);
		List<Horario> horarios = horarioRepositorio.listarParaGerarAtividades(atividadeDTO.getIdTurma(), atividadeDTO.getIdDisciplina(), 
				atividadeDTO.getIdPessoaUsuarioLogado());
		Long idCalendario = calendarioRepositorio.recuperarIdPeriodoExecucao(atividadeDTO.getIdPeriodoExecucao());
		List<LocalDateTime> diasNaoLetivos = eventoRepositorio.listarDataHoraEventosDiasNaoLetivos(idCalendario);
		
		if (horarios != null && !horarios.isEmpty()) {
			horarios.stream().forEach(horario -> {
				LocalDate dataInicial = horario.getDataInicial();
				while (dataInicial.isBefore(horario.getDataFinal())) {
					boolean dataInicialDiaNaoLetivo = verificarSeDataInicialEDiaNaoLetivo(diasNaoLetivos, dataInicial);
					// se o diaSemana da dataCorrente for igual ao tipoDiaSemana do registroCorrente
					if (dataInicial.getDayOfWeek().getValue() == EnumTipoDiaSemana.porId(horario.getIdTipoDiaSemana()).id() && !dataInicialDiaNaoLetivo) {
						// verificar se existe atividade
						int countAtividade = atividadeRepositorio.verificarSeAtividadeExiste(FiltroAtividade.builder()
																			.dataPrevista(dataInicial)
																			.idHorario(horario.getId())
																			.idFuncionario(horario.getIdFuncionario())
																			.build());
						if (countAtividade == 0) {
							registrar(horario, dataInicial);
						}
					}
					dataInicial = dataInicial.plusDays(1);
				}
			});
		}

    }

	private boolean verificarSeDataInicialEDiaNaoLetivo(List<LocalDateTime> diasNaoLetivos, LocalDate dataInicial) {
		if (diasNaoLetivos !=null && !diasNaoLetivos.isEmpty()) {
			return diasNaoLetivos.stream().filter(dataHora -> {
				return dataHora.toLocalDate().equals(dataInicial);
			}).findAny().orElse(null) !=null ? true : false;
		}
		return false;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private void registrar(Horario horario, LocalDate dataCorrente) {
		Long idSubFaseExecucao = subFaseExecucaoRepositorio.buscarIdParaCriarAtividade(horario.getId(), dataCorrente);
		if (idSubFaseExecucao != null) {
			atividadeRepositorio.criar(Atividade.builder()
			        .dataPrevista(dataCorrente)   
			        .idHorario(horario.getId())
			        .idSubFaseExecucao(idSubFaseExecucao)
			        .idFuncionario(horario.getIdFuncionario()).build());
			log.info("###### ATIVIDADE CRIADA COM SUCESSO PARA O HORARIO - " + horario.getId());
		}
	}
	
	@Override
	public void confirmar(AtividadeDTO atividadeDTO) {
		atividadeDTO.setDataRealizacao(atividadeDTO.getDataPrevista());
		alterar(atividadeDTO);
	}
	
	@Override
	public Long alterar(AtividadeDTO atividadeDTO) {
		adicionarDadosUsuarioLogado(atividadeDTO, true);
		
		validarAlteracao(atividadeDTO);
		
		return atividadeRepositorio.alterar(Atividade.builder()
										.id(atividadeDTO.getId())
										.dataPrevista(atividadeDTO.getDataPrevista())                                
                                        .dataRealizacao(atividadeDTO.getDataRealizacao())
                                        .idHorario(atividadeDTO.getIdHorario())
                                        .idSubFaseExecucao(atividadeDTO.getIdSubFaseExecucao())
                                        .idFuncionario(atividadeDTO.getIdFuncionario()).build());

		
	}

	private void validarAlteracao(AtividadeDTO atividadeDTO) {
		boolean habilidadesCadastradas = atividadeRepositorio.verificarHabilidadesCadastradas(FiltroAtividade.builder()
																									.id(atividadeDTO.getId()).build());
		boolean diarioCadastrado = atividadeRepositorio.verificarDiarioCadastrado(FiltroAtividade.builder()
																									.id(atividadeDTO.getId()).build());
		boolean frequenciaCadastrada = atividadeRepositorio.verificarFrequenciaCadastrada(FiltroAtividade.builder()
																							.id(atividadeDTO.getId())
																							.anoTurma(atividadeDTO.getAnoTurma())
																							.idDisciplina(atividadeDTO.getIdDisciplina())
																							.build());
		
		StringBuilder mensagemValiacao = new StringBuilder("É necessário registrar");
		boolean temMensagem = false;
		
		if (!habilidadesCadastradas) {
			mensagemValiacao.append(" habilidade(s)");
			temMensagem = true;
		}
		
		if (!diarioCadastrado) {
			mensagemValiacao.append(temMensagem ? ", diário(s)" : " diário(s)");
			temMensagem = true;
		}
		
		if (!frequenciaCadastrada) {
			mensagemValiacao.append(temMensagem ? ", frequências(s)" : " frequências(s)");
			temMensagem = true;
		}
		mensagemValiacao.append(" antes de confirmar.");
		
		if (temMensagem) {
			throw new ExcecaoDeNegocio(mensagemValiacao.toString());
		}
	}

	@Override
	public AtividadeDTO buscarPorId (long id) {
		AtividadeDTO dto = atividadeRepositorio.buscarPorId(id);
		dto.setHorasAtividades(atividadeRepositorio.recuperarHorasAtividades(id));
		return dto;
	}

	@Override
	public PaginacaoDTO<AtividadeDTO> listar(FiltroAtividade filtroAtividade) {
		int total = atividadeRepositorio.contar(filtroAtividade);
		
		return PaginacaoDTO.<AtividadeDTO>builder().total(total).dados(atividadeRepositorio.listar(filtroAtividade)).build();
	}

	@Override
	public boolean excluir(Long id) {
		return atividadeRepositorio.excluir(id);
	}

	@Override
	public List<AtividadeDisciplinaDireitoDTO> listarAtividadesDisciplinasDireitos(FiltroAtividade filtroAtividade) {
		return atividadeRepositorio.listarAtividadesDisciplinasDireitos((filtroAtividade));
	}
	
	@Override
	public void criarAtividadeDisciplinaDireito(AtividadeDisciplinaDireitoDTO dto) {

		if (dto.getIdsDisciplinaDireito() != null && !dto.getIdsDisciplinaDireito().isEmpty()) {
			dto.getIdsDisciplinaDireito().stream().forEach(idDisciplinaDireito -> {
				atividadeRepositorio.criarAtividadeDisciplinaDireito(AtividadeDisciplinaDireito.builder()
                        .idDisciplinaDireito(idDisciplinaDireito)                               
                        .idAtividade(dto.getIdAtividade()).build());	
			});
		}

    }

	@Override
	public boolean excluirAtividadeDisciplinaDireito(Long idDisciplinaDireito, Long idAtividade) {
		atividadeRepositorio.excluirAtividadeDisciplinaDireito(idDisciplinaDireito, idAtividade);
		return true;
	}
	
	@Override
	public List<AtividadeDisciplinaObjetivoDTO> listarAtividadesDisciplinasObjetivos(FiltroAtividade filtroAtividade) {
		return atividadeRepositorio.listarAtividadesDisciplinasObjetivos((filtroAtividade));
	}
	
	@Override
	public void criarAtividadeDisciplinaObjetivo(AtividadeDisciplinaObjetivoDTO dto) {

		if (dto.getIdsDisciplinaObjetivo() != null && !dto.getIdsDisciplinaObjetivo().isEmpty()) {
			dto.getIdsDisciplinaObjetivo().stream().forEach(idDisciplinaObjetivo -> {
				atividadeRepositorio.criarAtividadeDisciplinaObjetivo(AtividadeDisciplinaObjetivo.builder()
                        .idDisciplinaObjetivo(idDisciplinaObjetivo)                               
                        .idAtividade(dto.getIdAtividade()).build());	
			});
		}

    }

	@Override
	public boolean excluirAtividadeDisciplinaObjetivo(Long idDisciplinaObjetivo, Long idAtividade) {
		atividadeRepositorio.excluirAtividadeDisciplinaObjetivo(idDisciplinaObjetivo, idAtividade);
		return true;
	}
	
	@Override
	public List<AtividadeDisciplinaCompetenciaDTO> listarAtividadesDisciplinasCompetencias(FiltroAtividade filtroAtividade) {
		return atividadeRepositorio.listarAtividadesDisciplinasCompetencias((filtroAtividade));
	}
	
	@Override
	public void criarAtividadeDisciplinaCompetencia(AtividadeDisciplinaCompetenciaDTO dto) {

		if (dto.getIdsDisciplinaCompetencia() != null && !dto.getIdsDisciplinaCompetencia().isEmpty()) {
			dto.getIdsDisciplinaCompetencia().stream().forEach(idDisciplinaCompetencia -> {
				atividadeRepositorio.criarAtividadeDisciplinaCompetencia(AtividadeDisciplinaCompetencia.builder()
                        .idDisciplinaCompetencia(idDisciplinaCompetencia)                               
                        .idAtividade(dto.getIdAtividade()).build());	
			});
		}

    }

	@Override
	public boolean excluirAtividadeDisciplinaCompetencia(Long idDisciplinaCompetencia, Long idAtividade) {
		atividadeRepositorio.excluirAtividadeDisciplinaCompetencia(idDisciplinaCompetencia, idAtividade);
		return true;
	}
	
	@Override
	public List<AtividadeDisciplinaHabilidadeDTO> listarAtividadesDisciplinasHabilidades(FiltroAtividade filtroAtividade) {
		return atividadeRepositorio.listarAtividadesDisciplinasHabilidades((filtroAtividade));
	}
	
	@Override
	public void criarAtividadeDisciplinaHabilidade(AtividadeDisciplinaHabilidadeDTO dto) {

		if (dto.getIdsDisciplinaHabilidade() != null && !dto.getIdsDisciplinaHabilidade().isEmpty()) {
			dto.getIdsDisciplinaHabilidade().stream().forEach(idDisciplinaHabilidade -> {
				atividadeRepositorio.criarAtividadeDisciplinaHabilidade(AtividadeDisciplinaHabilidade.builder()
                        .idDisciplinaHabilidade(idDisciplinaHabilidade)                               
                        .idAtividade(dto.getIdAtividade()).build());	
			});
		}

    }

	@Override
	public boolean excluirAtividadeDisciplinaHabilidade(Long idDisciplinaHabilidade, Long idAtividade) {
		atividadeRepositorio.excluirAtividadeDisciplinaHabilidade(idDisciplinaHabilidade, idAtividade);
		return true;
	}

	@Override
	public List<AtividadeDTO> listarDatasAtividades(FiltroAtividade filtroAtividade) {
		return atividadeRepositorio.listarDatasAtividades(filtroAtividade);
	}

	@Override
	public List<String> listarHorasIniciaisAtividades(Long idAtividade) {
		return atividadeRepositorio.listarHorasIniciaisAtividades(idAtividade);
	}

	@Override
	public boolean excluirDiasNaoLetivos(FiltroAtividade filtroAtividade) {
		filtroAtividade.setIdPessoaUsuarioLogado(servicoAutenticacao.buscarUsuarioLogado().getIdPessoa());
		Long idCalendario = calendarioRepositorio.recuperarIdPeriodoExecucao(filtroAtividade.getIdPeriodoExecucao());
		filtroAtividade.setIdCalendario(idCalendario);
		return atividadeRepositorio.excluirDiasNaoLetivos(filtroAtividade);
	}
	
	@Override
	public boolean verificarSePodeAddAvaliacaoDeDesempenho(FiltroAtividade filtroAtividade) {
		return atividadeRepositorio.verificarSePodeAddAvaliacaoDeDesempenho((filtroAtividade));
	}

}

