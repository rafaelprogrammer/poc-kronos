package br.com.kronos.backend.aplicacao.pessoa;

import java.util.List;

import br.com.kronos.backend.aplicacao.pessoa.api.FiliacaoDTO;

public interface FiliacaoRepositorio {
	
	int criar(Filiacao filiacao);
	List<FiliacaoDTO> listar(FiltroFiliacao filtroFiliacao);
	public int contar(FiltroFiliacao filtro);
	boolean excluir(int id);

}