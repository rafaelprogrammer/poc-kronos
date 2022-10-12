package br.com.kronos.backend.aplicacao.comum;

import java.util.List;

public interface PaisRepositorio {
	
	List<Pais> listar(FiltroPais filtroPais);

}
