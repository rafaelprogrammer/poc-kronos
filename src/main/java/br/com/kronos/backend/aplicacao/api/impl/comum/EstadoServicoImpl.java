package br.com.kronos.backend.aplicacao.api.impl.comum;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.EstadoDTO;
import br.com.kronos.backend.aplicacao.comum.EstadoRepositorio;
import br.com.kronos.backend.aplicacao.comum.FiltroEstado;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class EstadoServicoImpl implements EstadoServico {
	
	@NonNull
	private EstadoRepositorio estadoRepositorio;
	
	@NonNull
	private ModelMapper modelMapper;

	@Override
	public List<EstadoDTO> listar(FiltroEstado filtroEstado) throws ExcecaoDeNegocio {
		try {
			List<EstadoDTO> estadosDTOS = modelMapper.map(estadoRepositorio.listar(filtroEstado),
					new TypeToken<List<EstadoDTO>>() {
					}.getType());
			
			return estadosDTOS;
			
		} catch (RuntimeException e) {
			log.error("Erro ao listar estados", e);
			throw new ExcecaoDeNegocio("Erro ao listar estados", e);
		}
	}

}
