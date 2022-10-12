package br.com.kronos.backend.aplicacao.horario;

import java.util.List;

import br.com.kronos.backend.aplicacao.horario.api.HoraAtividadeDiaDaSemanaDTO;

public interface ConsultasHorariosDiaDaSemanaRepositorio {

	StringBuilder listarDosDiasDaSemana(FiltroHorario filtro);
	StringBuilder listarDosDiasDaSemanaJaOcupadosPorOutroProfessor(FiltroHorario filtro);
	StringBuilder listarDosDiasDaSemanaQueEstaoLivres(FiltroHorario filtro);
	StringBuilder listarDosDiasDaSemanaOcupadosPeloProfessorEmOutraTurma(FiltroHorario filtro);
	List<HoraAtividadeDiaDaSemanaDTO> listarHorasAtividadesDiaDaSemana(FiltroHorario filtro);
	StringBuilder listarDosDiasDaSemanaNaoEditavel(FiltroHorario filtro);
}
