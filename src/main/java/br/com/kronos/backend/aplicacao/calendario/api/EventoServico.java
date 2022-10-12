package br.com.kronos.backend.aplicacao.calendario.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.calendario.FiltroEvento;


public interface EventoServico {
	
	Long criar(EventoDTO eventoDTO);
	Long alterar(EventoDTO eventoDTO);
	EventoDTO buscarPorId(long id);
	List<EventoDTO>listar(FiltroEvento filtroEvento);
	boolean excluir(Long id);
	public List<CategoriaEventoDTO> listarCatetoria();
	void validarIntervaloCalendario(EventoDTO eventoDTO);
	
}
