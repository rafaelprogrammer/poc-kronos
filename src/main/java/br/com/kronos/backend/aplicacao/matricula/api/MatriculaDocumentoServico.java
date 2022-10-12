package br.com.kronos.backend.aplicacao.matricula.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.matricula.FiltroMatriculaDocumento;
import br.com.kronos.backend.aplicacao.matricula.api.MatriculaDocumentoDTO;


public interface MatriculaDocumentoServico {
	
	Long criar(MatriculaDocumentoDTO matriculaDocumentoDTO) throws ExcecaoDeNegocio;
	Long alterar(MatriculaDocumentoDTO matriculaDocumentoDTO)throws ExcecaoDeNegocio;
	MatriculaDocumentoDTO buscarPorId(long id);
	PaginacaoDTO<MatriculaDocumentoDTO>listar(FiltroMatriculaDocumento filtroMatriculaDocumento) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	
}
