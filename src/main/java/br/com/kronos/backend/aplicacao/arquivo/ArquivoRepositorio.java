package br.com.kronos.backend.aplicacao.arquivo;

import java.util.List;

import br.com.kronos.backend.aplicacao.arquivo.api.ArquivoDTO;

public interface ArquivoRepositorio {

	int criar (Arquivo arquivo);
	boolean excluir (int id);
	ArquivoDTO buscarPorId(int id);
	List<ArquivoDTO> listar(FiltroArquivo filtro);
	public int contar(FiltroArquivo filtro);
	
}
