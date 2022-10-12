package br.com.kronos.backend.aplicacao.diario;

import java.util.List;

import br.com.kronos.backend.aplicacao.diario.api.DiarioDTO;

public interface DiarioRepositorio {
	
	Long criar(Diario Diario);
	Long alterar(Diario Diario);
	DiarioDTO buscarPorId(Long id);
	List<DiarioDTO> listar(FiltroDiario filtroDiario);
	boolean excluir(Long id);
	int contar(FiltroDiario filtroDiario);

}