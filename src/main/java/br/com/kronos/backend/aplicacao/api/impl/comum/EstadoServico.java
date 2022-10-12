package br.com.kronos.backend.aplicacao.api.impl.comum;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.EstadoDTO;
import br.com.kronos.backend.aplicacao.comum.FiltroEstado;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;

public interface EstadoServico {
	
	List<EstadoDTO> listar(FiltroEstado filtroEstado) throws ExcecaoDeNegocio;

}
