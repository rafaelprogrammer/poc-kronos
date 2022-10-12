package br.com.kronos.backend.aplicacao.horario.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.diario.AtividadeRepositorio;
import br.com.kronos.backend.aplicacao.diario.FiltroAtividade;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.horario.ConsultasHorariosDiaDaSemanaRepositorio;
import br.com.kronos.backend.aplicacao.horario.EnumTipoDiaSemana;
import br.com.kronos.backend.aplicacao.horario.FiltroHorario;
import br.com.kronos.backend.aplicacao.horario.Horario;
import br.com.kronos.backend.aplicacao.horario.HorarioHoraAtividade;
import br.com.kronos.backend.aplicacao.horario.HorarioHoraAtividadeRepositorio;
import br.com.kronos.backend.aplicacao.horario.HorarioRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class HorarioServicoImpl implements HorarioServico {

	@NonNull
	private HorarioRepositorio horarioRepositorio;
	
	@NonNull
	private ConsultasHorariosDiaDaSemanaRepositorio consultasHorariosDiaDaSemanaRepositorio;
	
	@NonNull
	private HorarioHoraAtividadeRepositorio horarioHoraAtividadeRepositorio;
	
	@NonNull
	private AtividadeRepositorio atividadeRepositorio;

	@NonNull
	private ModelMapper modelMapper;
	
	private Long idHorario;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void vincularHorarioHoraAtividade(DadosVinculacaoHorariosHorasAtividadesDTO dto) {
		
		vincularHorarioHoraAtividadeDiaDaSemana(EnumTipoDiaSemana.DOMINGO, dto.getHorario(), dto.getHorariosDiaDaSemana().getDomingo());
		vincularHorarioHoraAtividadeDiaDaSemana(EnumTipoDiaSemana.SEGUNDA, dto.getHorario(), dto.getHorariosDiaDaSemana().getSegunda());
		vincularHorarioHoraAtividadeDiaDaSemana(EnumTipoDiaSemana.TERCA, dto.getHorario(), dto.getHorariosDiaDaSemana().getTerca());
		vincularHorarioHoraAtividadeDiaDaSemana(EnumTipoDiaSemana.QUARTA, dto.getHorario(), dto.getHorariosDiaDaSemana().getQuarta());
		vincularHorarioHoraAtividadeDiaDaSemana(EnumTipoDiaSemana.QUINTA, dto.getHorario(), dto.getHorariosDiaDaSemana().getQuinta());
		vincularHorarioHoraAtividadeDiaDaSemana(EnumTipoDiaSemana.SEXTA, dto.getHorario(), dto.getHorariosDiaDaSemana().getSexta());
		vincularHorarioHoraAtividadeDiaDaSemana(EnumTipoDiaSemana.SABADO, dto.getHorario(), dto.getHorariosDiaDaSemana().getSabado());
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	private void vincularHorarioHoraAtividadeDiaDaSemana(EnumTipoDiaSemana diaDaSemana, HorarioDTO horario, List<HoraAtividadeDiaDaSemanaDTO> horasAtividades) {
		idHorario = null;
		boolean salvaHorario = horasAtividades.stream()
			.filter(hora -> hora.getIdHorario() == null && !hora.isBloqueado() && hora.isSelecionado()).findFirst().isPresent();
		if (salvaHorario) {
			horario.setIdTipoDiaSemana(diaDaSemana.id());
			idHorario = criar(horario);
		}
		horasAtividades.stream().forEach(hora -> {
			if (hora.getIdHorario() == null && !hora.isBloqueado() && hora.isSelecionado()) {
				horarioHoraAtividadeRepositorio.criar(HorarioHoraAtividade.builder()
														.idHorario(idHorario)
														.idHoraAtividade(hora.getIdAtividade())
														.build());
			}
			//Desmarcando quando estiver selecionado
			if (hora.getIdHorario() != null && !hora.isBloqueado() && !hora.isSelecionado()) {
				horarioHoraAtividadeRepositorio.excluir(hora.getIdHorario(), hora.getIdAtividade());
			}
		});
	}

	private Long criar(HorarioDTO horarioDTO) {
			return horarioRepositorio.criar(Horario.builder()
					                        .dataInicial(horarioDTO.getDataInicial())                                
                                            .dataFinal(horarioDTO.getDataFinal())
                                            .regular(true)
                                            .idFuncionario(horarioDTO.getIdFuncionario())
                                            .idDisciplina(horarioDTO.getIdDisciplina())
                                            .idTurma(horarioDTO.getIdTurma())
                                            .idTipoDiaSemana(horarioDTO.getIdTipoDiaSemana())
                                            .idTipoRegimeLetivo(horarioDTO.getIdTipoRegimeLetivo()).build());	

    }
	
//	@Override
//	public Long alterar(HorarioDTO horarioDTO) throws ExcecaoDeNegocio {
//			return horarioRepositorio.alterar(Horario.builder()
//											.id(horarioDTO.getId())
//											.dataInicial(horarioDTO.getDataInicial())                                
//                                            .dataFinal(horarioDTO.getDataFinal())
//                                            .regular(horarioDTO.isRegular())
//                                            .idFuncionario(horarioDTO.getIdFuncionario())
//                                            .idDisciplina(horarioDTO.getIdDisciplina())
//                                            .idTurma(horarioDTO.getIdTurma())
//                                            .idTipoDiaSemana(horarioDTO.getIdTipoDiaSemana())
//                                            .idTipoRegimeLetivo(horarioDTO.getIdTipoRegimeLetivo()).build());	
//
//		
//	}

	@Override
	public PaginacaoDTO<HorarioDTO> listar(FiltroHorario filtroHorario) throws ExcecaoDeNegocio {
		filtroHorario.setRegular(true);
		int total = horarioRepositorio.contar(filtroHorario);
		return PaginacaoDTO.<HorarioDTO>builder().total(total).dados(horarioRepositorio.listar(filtroHorario)).build();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean excluir(Long idHorario, Long idHoraAtividade) {
		horarioHoraAtividadeRepositorio.excluir(idHorario, idHoraAtividade);
		horarioRepositorio.excluir(idHorario);
		return true;
	}

	@Override
	public void validarVinculoComAtividade(Long idHorario) {
		long countAtividades = atividadeRepositorio.contar(FiltroAtividade.builder().idHorario(idHorario).build());
		if (countAtividades > 0) {
			throw new ExcecaoDeNegocio("Não é possível excluir horário, pois existe uma ou mais atividades vinculadas");
		}
	}

	@Override
	public HorariosDiaDaSemanaDTO carregarHorariosDiaDaSemana(FiltroHorario filtro) {
		
		if (filtro.isInclusao()) {
			boolean existeHorario = horarioRepositorio.verificarSeExisteHorario(filtro);
			
			if (existeHorario) {
				throw new ExcecaoDeNegocio("Já existe(m) horário(s) com intervalo total ou parcial para este professor/disciplina/turma");
			}
		}
		
		return HorariosDiaDaSemanaDTO.builder()
				.domingo(listarHorasAtividadesDiaDaSemana(filtro, EnumTipoDiaSemana.DOMINGO))
				.segunda(listarHorasAtividadesDiaDaSemana(filtro, EnumTipoDiaSemana.SEGUNDA))
				.terca(listarHorasAtividadesDiaDaSemana(filtro, EnumTipoDiaSemana.TERCA))
				.quarta(listarHorasAtividadesDiaDaSemana(filtro, EnumTipoDiaSemana.QUARTA))
				.quinta(listarHorasAtividadesDiaDaSemana(filtro, EnumTipoDiaSemana.QUINTA))
				.sexta(listarHorasAtividadesDiaDaSemana(filtro, EnumTipoDiaSemana.SEXTA))
				.sabado(listarHorasAtividadesDiaDaSemana(filtro, EnumTipoDiaSemana.SABADO))
				.build();
	}
	
	private List<HoraAtividadeDiaDaSemanaDTO> listarHorasAtividadesDiaDaSemana(FiltroHorario filtro, EnumTipoDiaSemana diaDaSemana) {
		filtro.setIdTipoDiaSemana(diaDaSemana.id());
		return consultasHorariosDiaDaSemanaRepositorio.listarHorasAtividadesDiaDaSemana(filtro);
	}

}
