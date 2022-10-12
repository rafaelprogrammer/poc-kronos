package br.com.kronos.backend.aplicacao.matricula;

import java.util.List;

import br.com.kronos.backend.aplicacao.matricula.api.MatriculaCanceladaTransferidaDTO;
import br.com.kronos.backend.aplicacao.matricula.api.MatriculaContratoDTO;
import br.com.kronos.backend.aplicacao.matricula.api.MatriculaDTO;
import br.com.kronos.backend.aplicacao.matricula.api.MatriculaOcorrenciaDTO;

public interface MatriculaRepositorio {
	
	Long criar(Matricula matricula);
	Long alterar(Matricula matricula);
	MatriculaDTO buscarPorId(Long id);
	List<MatriculaDTO> listar(FiltroMatricula filtroMatricula);
	boolean excluir(Long id);
	int contar(FiltroMatricula filtroMatricula);
	boolean verificarSeExisteMatricula(Long idPessoa, Long idCurso);
	void atualizarTipoSituacao(Long id, EnumTipoSituacaoMatricula tipoSituacaoMatricula);
	List<MatriculaContratoDTO> listarPendentesContrato(Long idInstituicao, Integer... idsTiposSituacoesContratos);
	MatriculaOcorrenciaDTO buscarMatriculaParaOcorrencia(Long idPessoaSelecionada);
	List<MatriculaOcorrenciaDTO> listarMatriculaParaOcorrenciaCombo(Long idPessoaSelecionada);
	MatriculaCanceladaTransferidaDTO buscarPorIdParaCancelamentoOuTransferencia(Long id);

}