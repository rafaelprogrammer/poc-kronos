package br.com.kronos.backend.aplicacao.calendario.api;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.calendario.CalendarioRepositorio;
import br.com.kronos.backend.aplicacao.calendario.Evento;
import br.com.kronos.backend.aplicacao.calendario.EventoRepositorio;
import br.com.kronos.backend.aplicacao.calendario.FiltroEvento;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class EventoServicoImpl implements EventoServico {

	@NonNull
	private EventoRepositorio eventoRepositorio;
	
	@NonNull
	private CalendarioRepositorio calendarioRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(EventoDTO eventoDTO) throws ExcecaoDeNegocio {
		
		return eventoRepositorio.criar(Evento.builder()
				                        .dataHoraInicio(eventoDTO.getDataHoraInicio())
				                        .dataHoraFinal(eventoDTO.getDataHoraFinal())
				                        .diaLetivo(eventoDTO.isDiaLetivo())  
				                        .descricao(eventoDTO.getDescricao())  
				                        .idCategoriaEvento(eventoDTO.getIdCategoriaEvento())  
				                        .idCalendario(eventoDTO.getIdCalendario()).build());	

    }

	@Override
	public void validarIntervaloCalendario(EventoDTO eventoDTO) {
		CalendarioDTO calendario = calendarioRepositorio.buscarPorId(eventoDTO.getIdCalendario());
		LocalDate dataInicioEvento = eventoDTO.getDataHoraInicio().toLocalDate();
		LocalDate dataFinalEvento = eventoDTO.getDataHoraFinal().toLocalDate();
		if (dataInicioEvento.isBefore(calendario.getDataInicio()) || dataInicioEvento.isAfter(calendario.getDataFinal()) ||
			dataFinalEvento.isBefore(calendario.getDataInicio()) || dataFinalEvento.isAfter(calendario.getDataFinal())) {
			
			throw new ExcecaoDeNegocio("O período do evento deve ser de acordo com o período do calendário");
		}
	}
	
	@Override
	public Long alterar(EventoDTO eventoDTO) throws ExcecaoDeNegocio {
		return eventoRepositorio.alterar(Evento.builder()
										.id(eventoDTO.getId())
										.dataHoraInicio(eventoDTO.getDataHoraInicio()) 
										.dataHoraFinal(eventoDTO.getDataHoraFinal())
				                        .diaLetivo(eventoDTO.isDiaLetivo())  
				                        .descricao(eventoDTO.getDescricao())  
				                        .idCategoriaEvento(eventoDTO.getIdCategoriaEvento())
				                        .idCalendario(eventoDTO.getIdCalendario()).build());
	}

	@Override
	public EventoDTO buscarPorId (long id) {
		return eventoRepositorio.buscarPorId(id);
	}

	@Override
	public List<EventoDTO> listar(FiltroEvento filtroEvento) throws ExcecaoDeNegocio {
		return eventoRepositorio.listar(filtroEvento);
	}

	@Override
	public boolean excluir(Long id) {
		return eventoRepositorio.excluir(id);
	}

	@Override
	public List<CategoriaEventoDTO> listarCatetoria() {
		return modelMapper.map(eventoRepositorio.listarCatetoria(),
				new TypeToken<List<CategoriaEventoDTO>>() {
				}.getType());
	}

}

