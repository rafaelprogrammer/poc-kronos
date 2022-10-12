package br.com.kronos.backend.aplicacao.disciplina.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.disciplina.FiltroDisciplinaObjetivo;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaObjetivoDTO;


public interface DisciplinaObjetivoServico {
	
	void criar(DisciplinaObjetivoDTO diarioDTO) throws ExcecaoDeNegocio;
	Long alterar(DisciplinaObjetivoDTO diarioDTO)throws ExcecaoDeNegocio;
	DisciplinaObjetivoDTO buscarPorId(long id);
	PaginacaoDTO<DisciplinaObjetivoDTO>listar(FiltroDisciplinaObjetivo filtroDisciplinaObjetivo) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	PaginacaoDTO<DisciplinaObjetivoDTO> listarParaAtividadeDisciplinaObjetivo(
			FiltroDisciplinaObjetivo filtroDisciplinaObjetivo);
	
}