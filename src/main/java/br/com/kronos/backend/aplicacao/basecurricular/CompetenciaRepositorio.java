package br.com.kronos.backend.aplicacao.basecurricular;

import java.util.List;

import br.com.kronos.backend.aplicacao.basecurricular.api.CompetenciaDTO;

public interface CompetenciaRepositorio {
	
	Long criar(Competencia competencia);
	Long alterar(Competencia competencia);
	CompetenciaDTO buscarPorId(Long id);
	List<CompetenciaDTO> listar(FiltroCompetencia filtroCompetencia);
	boolean excluir(Long id);
	int contar(FiltroCompetencia filtroCompetencia);
	List<CompetenciaDTO> listarPorIds(List<Long> ids);
	List<CompetenciaDTO> listarParaDisciplinaCompetencia(FiltroCompetencia filtroCompetencia);
	int contarParaDisciplinaCompetencia(FiltroCompetencia filtroCompetencia);

}