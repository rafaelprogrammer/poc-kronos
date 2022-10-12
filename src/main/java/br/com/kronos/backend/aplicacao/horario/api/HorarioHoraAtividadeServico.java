package br.com.kronos.backend.aplicacao.horario.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.horario.FiltroHorarioHoraAtividade;
import br.com.kronos.backend.aplicacao.horario.api.HorarioHoraAtividadeDTO;


public interface HorarioHoraAtividadeServico {
	
	void criar(List<HorarioHoraAtividadeDTO> dtos);
	PaginacaoDTO<HorarioHoraAtividadeDTO> listar(FiltroHorarioHoraAtividade filtroHorarioHoraAtividade);
	boolean excluir(long idHorario, long idHoraAtividade);
}

