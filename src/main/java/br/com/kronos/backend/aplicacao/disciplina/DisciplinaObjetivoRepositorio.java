package br.com.kronos.backend.aplicacao.disciplina;

import java.util.List;

import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaObjetivoDTO;

public interface DisciplinaObjetivoRepositorio {
	
	Long criar(DisciplinaObjetivo DisciplinaObjetivo);
	Long alterar(DisciplinaObjetivo DisciplinaObjetivo);
	DisciplinaObjetivoDTO buscarPorId(Long id);
	List<DisciplinaObjetivoDTO> listar(FiltroDisciplinaObjetivo filtroDisciplinaObjetivo);
	boolean excluir(Long id);
	int contar(FiltroDisciplinaObjetivo filtroDisciplinaObjetivo);
	List<DisciplinaObjetivoDTO> listarParaAtividadeDisciplinaObjetivo(
			FiltroDisciplinaObjetivo filtroDisciplinaObjetivo);
	int contarParaAtividadeDisciplinaObjetivo(FiltroDisciplinaObjetivo filtroDisciplinaObjetivo);

}
