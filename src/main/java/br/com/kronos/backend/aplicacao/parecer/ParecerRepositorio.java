package br.com.kronos.backend.aplicacao.parecer;

import java.util.List;

import br.com.kronos.backend.aplicacao.parecer.FiltroParecer;

public interface ParecerRepositorio {
	
	Long criar(Parecer parecer);
	Long alterar(Parecer parecer);
	Parecer buscarPorId(Long id);
	List<Parecer> listar(FiltroParecer filtroParecer);
	boolean excluir(Long id);
	int contar(FiltroParecer filtroParecer);

}