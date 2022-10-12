package br.com.kronos.backend.aplicacao.horario.api;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.horario.FiltroHorario;
import br.com.kronos.backend.aplicacao.horario.FiltroSubstituto;
import br.com.kronos.backend.aplicacao.horario.Horario;
import br.com.kronos.backend.aplicacao.horario.HorarioRepositorio;
import br.com.kronos.backend.aplicacao.horario.Substituto;
import br.com.kronos.backend.aplicacao.horario.SubstitutoRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class SubstitutoServicoImpl implements SubstitutoServico {

	@NonNull
	private SubstitutoRepositorio substitutoRepositorio;
	
	@NonNull
	private HorarioRepositorio horarioRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void criar(SubstitutoDTO substitutoDTO) {
		Horario horario = horarioRepositorio.buscarPorId(substitutoDTO.getIdHorario());
		List<Long> idsHorarios = horarioRepositorio.listarIdsHorario(FiltroHorario.builder()
								.idDisciplina(horario.getIdDisciplina())
								.idFuncionario(horario.getIdFuncionario())
								.idTipoRegimeLetivo(horario.getIdTipoRegimeLetivo())
								.idTurma(horario.getIdTurma())
								.dataInicial(horario.getDataInicial())
								.dataFinal(horario.getDataFinal())
								.build());
		idsHorarios.stream().forEach((idHorario) -> {
			substitutoRepositorio.criar(Substituto.builder()
                    .dataInicial(substitutoDTO.getDataInicial())                                
                    .dataFinal(substitutoDTO.getDataFinal())
                    .idFuncionario(substitutoDTO.getIdFuncionario())
                    .idHorario(idHorario).build());
		});
    }
	
	@Override
	public Long alterar(SubstitutoDTO substitutoDTO) {
			return substitutoRepositorio.alterar(Substituto.builder()
											.id(substitutoDTO.getId())
											.dataInicial(substitutoDTO.getDataInicial())                                
                                            .dataFinal(substitutoDTO.getDataFinal())
                                            .idFuncionario(substitutoDTO.getIdFuncionario())
                                            .idHorario(substitutoDTO.getIdHorario()).build());	
	}
	
	@Override
	public void validarDuplicidade(SubstitutoDTO substitutoDTO) {
		long countSubstitutos = substitutoRepositorio.verificarSeExisteSubstituto(FiltroSubstituto.builder()
										.idFuncionario(substitutoDTO.getIdFuncionario())
										.idHorario(substitutoDTO.getIdHorario())
										.dataInicial(substitutoDTO.getDataInicial())
										.dataFinal(substitutoDTO.getDataFinal())
										.build());
		if (countSubstitutos > 0) {
			throw new ExcecaoDeNegocio("Substituto já registrado neste horário e período");
		}
	}
	
	@Override
	public void validarIntervaloHorario(SubstitutoDTO dto) {
		Horario horario = horarioRepositorio.buscarPorId(dto.getIdHorario());
		LocalDate dataInicioSubstituto = dto.getDataInicial();
		LocalDate dataFinalSubstituto = dto.getDataFinal();
		if (dataInicioSubstituto.isBefore(horario.getDataInicial()) || dataInicioSubstituto.isAfter(horario.getDataFinal()) ||
			dataFinalSubstituto.isBefore(horario.getDataInicial()) || dataFinalSubstituto.isAfter(horario.getDataFinal())) {
			
			throw new ExcecaoDeNegocio("O período do substituto deve ser de acordo com o período do horário");
		}
	}

	@Override
	public SubstitutoDTO buscarPorId (long id) {
		return substitutoRepositorio.buscarPorId(id);
	}

	@Override
	public PaginacaoDTO<SubstitutoDTO> listar(FiltroSubstituto filtroSubstituto) {
			int total = substitutoRepositorio.contar(filtroSubstituto);
			
			return  PaginacaoDTO.<SubstitutoDTO>builder().total(total).dados(substitutoRepositorio.listar(filtroSubstituto)).build();
	}

	@Override
	public boolean excluir(DadosExcluiSubstitutoDTO dados) {
		return substitutoRepositorio.excluir(dados);
	}

	@Override
	public List<SubstitutoResumoDTO> listarParaProfessoresHorario(Long idDisciplina, Long idFuncionarioDoHorario) {
		return substitutoRepositorio.listarParaProfessoresHorario(idDisciplina, idFuncionarioDoHorario);
	}

}
