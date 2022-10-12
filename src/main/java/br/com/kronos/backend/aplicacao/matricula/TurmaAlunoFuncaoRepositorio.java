package br.com.kronos.backend.aplicacao.matricula;

import java.util.List;

public interface TurmaAlunoFuncaoRepositorio {
	
	Long criar(TurmaAlunoFuncao turmaAlunoFuncao);
	Long alterar(TurmaAlunoFuncao turmaAlunoFuncao);
	TurmaAlunoFuncao buscarPorId(Long id);
	List<TurmaAlunoFuncao> listar(FiltroTurmaAlunoFuncao filtroTurmaAlunoFuncao);
	boolean excluir(Long id);
	int contar(FiltroTurmaAlunoFuncao filtroTurmaAlunoFuncao);

}