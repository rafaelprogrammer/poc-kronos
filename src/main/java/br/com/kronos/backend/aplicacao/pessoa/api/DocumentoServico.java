package br.com.kronos.backend.aplicacao.pessoa.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.pessoa.FiltroDocumento;

public interface DocumentoServico {
	
	int criar(DocumentoDTO dto);
	int alterar(DocumentoDTO dto);
	DocumentoDTO buscarPorId(int id);
	PaginacaoDTO<DocumentoDTO> listar(FiltroDocumento filtro);
	boolean excluir(int id);
	void alterarArquivo(DocumentoDTO dto);
}
