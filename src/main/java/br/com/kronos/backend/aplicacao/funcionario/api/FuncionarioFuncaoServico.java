package br.com.kronos.backend.aplicacao.funcionario.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.funcionario.FiltroFuncionarioFuncao;


public interface FuncionarioFuncaoServico {
	
	void criar(List<FuncionarioFuncaoDTO> dtos);
	List<FuncionarioFuncaoDTO>listar(FiltroFuncionarioFuncao filtroFuncionarioFuncao);
	void verificarSeExisteSomenteUmRegistroTrue(List<FuncionarioFuncaoDTO> dtos);
	
}
