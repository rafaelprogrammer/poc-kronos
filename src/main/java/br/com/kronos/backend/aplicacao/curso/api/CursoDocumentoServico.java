package br.com.kronos.backend.aplicacao.curso.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.curso.FiltroCursoDocumento;

public interface CursoDocumentoServico {
	
	Long criar(CursoDocumentoDTO dto);
	Long alterar(CursoDocumentoDTO dto);
	PaginacaoDTO<CursoDocumentoDTO>listar(FiltroCursoDocumento filtroCursoDocumento);
	boolean excluir(long id);
	CursoDocumentoDTO buscarPorId(long id);

}