package br.com.kronos.backend.aplicacao.matricula.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.matricula.FiltroMatricula;


public interface MatriculaServico {
	
	Long criar(MatriculaDTO matriculaDTO);
	Long alterar(MatriculaDTO matriculaDTO);
	MatriculaDTO buscarPorId(long id);
	PaginacaoDTO<MatriculaDTO>listar(FiltroMatricula filtroMatricula);
	boolean excluir(Long id);
	void validar(Long id);
	public List<MatriculaContratoDTO> listarPendentesContrato(Long idInstituicao);
	MatriculaOcorrenciaDTO buscarMatriculaParaOcorrencia(Long idPessoaSelecionada);
	List<MatriculaOcorrenciaDTO> listarMatriculaParaOcorrenciaCombo(Long idPessoaSelecionada);
	void reativar(Long id);
	MatriculaCanceladaTransferidaDTO buscarPorIdParaCancelamentoOuTransferencia(Long id);
	void cancelar(MatriculaCanceladaTransferidaDTO dto);
	void transferir(MatriculaCanceladaTransferidaDTO dto);
	
}
