package br.com.kronos.backend.aplicacao.curso;

import java.util.List;

public interface PortariaRepositorio {
	
	Long criar(Portaria portaria);
	Long alterar(Portaria portaria);
	Portaria buscarPorId(Long id);
	List<Portaria> listar(FiltroPortaria filtroPortaria);
	boolean excluir(Long id);
	int contar(FiltroPortaria filtroPortaria);

}