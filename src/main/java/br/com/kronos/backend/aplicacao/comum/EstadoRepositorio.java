package br.com.kronos.backend.aplicacao.comum;

import java.util.List;

public interface EstadoRepositorio {
	
	List<Estado> listar(FiltroEstado filtroEstado);

}
