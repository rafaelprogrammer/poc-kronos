package br.com.kronos.backend.aplicacao.matricula;

import java.util.List;

public interface MatriculaDocumentoRepositorio {
	
	Long criar(MatriculaDocumento matriculaDocumento);
	Long alterar(MatriculaDocumento matriculaDocumento);
	MatriculaDocumento buscarPorId(Long id);
	List<MatriculaDocumento> listar(FiltroMatriculaDocumento filtroMatriculaDocumento);
	boolean excluir(Long id);
	int contar(FiltroMatriculaDocumento filtroMatriculaDocumento);

}