package br.com.kronos.backend.aplicacao.pessoa;

import java.util.List;

import br.com.kronos.backend.aplicacao.pessoa.api.DocumentoDTO;

public interface DocumentoRepositorio {
	
	int criar(Documento documento);
	int alterar(Documento documento);
	DocumentoDTO buscarPorId(int id);
	List<DocumentoDTO> listar(FiltroDocumento filtro);
	boolean excluir(int id);
	int contar(FiltroDocumento filtro);
	int alterarArquivo(int id, int idArqAnexo);
}
