package br.com.kronos.backend.aplicacao.basecurricular.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroCompetencia;

public interface CompetenciaServico {
	
	Long criar(CompetenciaDTO competenciaDTO) throws ExcecaoDeNegocio;
	Long alterar(CompetenciaDTO competenciaDTO)throws ExcecaoDeNegocio;
	CompetenciaDTO buscarPorId(long id);
	PaginacaoDTO<CompetenciaDTO>listar(FiltroCompetencia filtroCompetencia) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	List<CompetenciaDTO> listarPorIds(List<Long> ids);
	PaginacaoDTO<CompetenciaDTO> listarParaDisciplinaCompetencia(FiltroCompetencia filtroCompetencia);

}
