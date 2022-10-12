package br.com.kronos.backend.aplicacao.curso;

import java.util.List;

public interface CursoEscalaRepositorio {
    
    int contarPorIdCursoEEscala(FiltroCursoEscala filtroCursoEscala);
	int criar(CursoEscala cursoEscala);
	List<CursoEscala> listar(FiltroCursoEscala filtroCursoEscala);
	int contar(FiltroCursoEscala filtroCursoEscala);
	boolean excluir(long idCurso, long idEscala);

}