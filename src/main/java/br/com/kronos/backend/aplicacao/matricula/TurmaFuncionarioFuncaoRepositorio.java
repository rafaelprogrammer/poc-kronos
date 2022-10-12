package br.com.kronos.backend.aplicacao.matricula;

import java.util.List;

public interface TurmaFuncionarioFuncaoRepositorio {
	
	Long criar(TurmaFuncionarioFuncao turmaFuncionarioFuncao);
	Long alterar(TurmaFuncionarioFuncao turmaFuncionarioFuncao);
	TurmaFuncionarioFuncao buscarPorId(Long id);
	List<TurmaFuncionarioFuncao> listar(FiltroTurmaFuncionarioFuncao filtroTurmaFuncionarioFuncao);
	boolean excluir(Long id);
	int contar(FiltroTurmaFuncionarioFuncao filtroTurmaFuncionarioFuncao);

}