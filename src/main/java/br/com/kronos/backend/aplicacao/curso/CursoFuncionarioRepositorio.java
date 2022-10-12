package br.com.kronos.backend.aplicacao.curso;

import java.util.List;

import br.com.kronos.backend.aplicacao.curso.api.CursoFuncionarioDTO;

public interface CursoFuncionarioRepositorio {
	
	Long criar(CursoFuncionario cursoFuncionario);
	Long alterar(CursoFuncionario cursoFuncionario);
	CursoFuncionarioDTO buscarPorId(Long id);
	List<CursoFuncionarioDTO> listar(FiltroCursoFuncionario filtroCursoFuncionario);
	boolean excluir(Long id);
	int contar(FiltroCursoFuncionario filtroCursoFuncionario);

}