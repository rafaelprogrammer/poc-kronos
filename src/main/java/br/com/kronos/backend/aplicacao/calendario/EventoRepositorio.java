package br.com.kronos.backend.aplicacao.calendario;

import java.time.LocalDateTime;
import java.util.List;

import br.com.kronos.backend.aplicacao.calendario.api.EventoDTO;

public interface EventoRepositorio {
	
	Long criar(Evento evento);
	Long alterar(Evento evento);
	EventoDTO buscarPorId(Long id);
	List<EventoDTO> listar(FiltroEvento filtroEvento);
	List<CategoriaEvento> listarCatetoria();
	boolean excluir(Long id);
	int contar(FiltroEvento filtroEvento);
	boolean excluir(FiltroEvento filtro);
	List<LocalDateTime> listarDataHoraEventosDiasNaoLetivos(Long idCalendario);

}