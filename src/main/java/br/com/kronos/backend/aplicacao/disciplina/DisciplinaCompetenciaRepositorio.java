package br.com.kronos.backend.aplicacao.disciplina;

import java.util.List;

import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaCompetenciaDTO;

public interface DisciplinaCompetenciaRepositorio {
	
	Long criar(DisciplinaCompetencia DisciplinaCompetencia);
	Long alterar(DisciplinaCompetencia DisciplinaCompetencia);
	DisciplinaCompetenciaDTO buscarPorId(Long id);
	List<DisciplinaCompetenciaDTO> listar(FiltroDisciplinaCompetencia filtroDisciplinaCompetencia);
	boolean excluir(Long id);
	int contar(FiltroDisciplinaCompetencia filtroDisciplinaCompetencia);
	List<DisciplinaCompetenciaDTO> listarParaAtividadeDisciplinaCompetencia(
			FiltroDisciplinaCompetencia filtroDisciplinaCompetencia);
	int contarParaAtividadeDisciplinaCompetencia(FiltroDisciplinaCompetencia filtroDisciplinaCompetencia);

}
