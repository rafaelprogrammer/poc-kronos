package br.com.kronos.backend.aplicacao.comum;

import java.util.List;

public interface CidadeRepositorio {
	
	List<Cidade> listar(FiltroCidade filtroCidade);
	int contar(FiltroCidade filtroCidade);

}
