package br.com.kronos.backend.aplicacao.curso;

import java.util.List;

public interface CursoDocumentoRepositorio {
	
	Long criar(CursoDocumento cursoDocumento);
	Long alterar(CursoDocumento cursoDocumento);
	CursoDocumento buscarPorId(Long id);
	List<CursoDocumento> listar(FiltroCursoDocumento filtroCursoDocumento);
	boolean excluir(Long id);
	int contar(FiltroCursoDocumento filtroCursoDocumento);

}