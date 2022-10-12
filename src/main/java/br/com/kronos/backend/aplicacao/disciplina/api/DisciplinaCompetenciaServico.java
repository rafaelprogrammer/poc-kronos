package br.com.kronos.backend.aplicacao.disciplina.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.disciplina.FiltroDisciplinaCompetencia;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaCompetenciaDTO;


public interface DisciplinaCompetenciaServico {
	
	void criar(DisciplinaCompetenciaDTO diarioDTO) throws ExcecaoDeNegocio;
	Long alterar(DisciplinaCompetenciaDTO diarioDTO)throws ExcecaoDeNegocio;
	DisciplinaCompetenciaDTO buscarPorId(long id);
	PaginacaoDTO<DisciplinaCompetenciaDTO>listar(FiltroDisciplinaCompetencia filtroDisciplinaCompetencia) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	PaginacaoDTO<DisciplinaCompetenciaDTO> listarParaAtividadeDisciplinaCompetencia(
			FiltroDisciplinaCompetencia filtroDisciplinaCompetencia);
	
}
