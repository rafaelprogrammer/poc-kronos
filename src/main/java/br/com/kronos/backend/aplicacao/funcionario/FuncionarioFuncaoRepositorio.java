package br.com.kronos.backend.aplicacao.funcionario;

import java.util.List;

import br.com.kronos.backend.aplicacao.funcionario.api.FuncionarioFuncaoDTO;

public interface FuncionarioFuncaoRepositorio {
	
	Long criar(FuncionarioFuncao funcionarioFuncao);
	List<FuncionarioFuncaoDTO> listar(FiltroFuncionarioFuncao filtroFuncionarioFuncao);
	boolean excluirPorIdFuncionario(Long idFuncionario);

}