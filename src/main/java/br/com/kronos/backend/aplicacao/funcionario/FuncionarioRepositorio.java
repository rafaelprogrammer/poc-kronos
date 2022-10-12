package br.com.kronos.backend.aplicacao.funcionario;

import java.time.LocalDate;
import java.util.List;

import br.com.kronos.backend.aplicacao.funcionario.api.FuncionarioDTO;;

public interface FuncionarioRepositorio {
	
	Long criar(Funcionario funcionario);
	Long alterar(Funcionario funcionario);
	FuncionarioDTO buscarPorId(Long id);
	List<FuncionarioDTO> listar(FiltroFuncionario filtroFuncionario);
	boolean excluir(Long id);
	int contar(FiltroFuncionario filtroFuncionario);
	FuncionarioDTO buscarPorIdPessoa(Long idPessoa);
	List<FuncionarioDTO> listarPorDisciplina(long idDisciplina);
	List<FuncionarioDTO> listarParaOcorrencia(LocalDate dataOcorrencia, Long idInstituicao);
	List<FuncionarioDTO> listarParaOcorrenciaResponsavelAnulacao(LocalDate dataOcorrencia, Long idInstituicao);
}