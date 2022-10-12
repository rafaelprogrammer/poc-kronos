package br.com.kronos.backend.aplicacao.funcionario.api;

import java.time.LocalDate;
import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.funcionario.FiltroFuncionario;
import br.com.kronos.backend.aplicacao.funcionario.api.FuncionarioDTO;


public interface FuncionarioServico {
	
	Long criar(FuncionarioDTO funcionarioDTO);
	Long alterar(FuncionarioDTO funcionarioDTO)throws ExcecaoDeNegocio;
	FuncionarioDTO buscarPorId(Long id);
	PaginacaoDTO<FuncionarioDTO>listar(FiltroFuncionario filtroFuncionario);
	boolean excluir(Long id);
	public List<FuncionarioDTO> listarPorDisciplina(long idDisciplina);
	List<FuncionarioDTO> listarParaOcorrenciaRelatores(LocalDate dataOcorrencia, Long idInstituicao);
	FuncionarioDTO buscarPorIdPessoa(Long idPessoa);
	List<FuncionarioDTO> listarParaOcorrenciaResponsavelAnulacao(LocalDate dataOcorrencia, Long idInstituicao);
}
