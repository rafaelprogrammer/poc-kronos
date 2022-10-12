package br.com.kronos.backend.aplicacao.disciplina;

import java.util.List;

public interface DisciplinaFuncionarioRepositorio {
    
    int contarPorIdDisciplinaEIdDisciplinaFuncionario(FiltroDisciplinaFuncionario filtroDisciplinaFuncionario);
	int criar(DisciplinaFuncionario disciplinaFuncionario);
	List<DisciplinaFuncionario> listar(FiltroDisciplinaFuncionario filtroDisciplinaFuncionario);
	int contar(FiltroDisciplinaFuncionario filtroDisciplinaFuncionario);
	boolean excluir(long idDisciplina, long idDisciplinaFuncionario);

}