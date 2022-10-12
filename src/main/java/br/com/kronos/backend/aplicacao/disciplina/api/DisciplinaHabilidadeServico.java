package br.com.kronos.backend.aplicacao.disciplina.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.disciplina.FiltroDisciplinaHabilidade;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;


public interface DisciplinaHabilidadeServico {
	
	void criar(DisciplinaHabilidadeDTO diarioDTO) throws ExcecaoDeNegocio;
	Long alterar(DisciplinaHabilidadeDTO diarioDTO)throws ExcecaoDeNegocio;
	DisciplinaHabilidadeDTO buscarPorId(long id);
	PaginacaoDTO<DisciplinaHabilidadeDTO>listar(FiltroDisciplinaHabilidade filtroDisciplinaHabilidade) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	PaginacaoDTO<DisciplinaHabilidadeDTO> listarParaAtividadeDisciplinaHabilidade(
			FiltroDisciplinaHabilidade filtroDisciplinaHabilidade);
	PaginacaoDTO<DisciplinaHabilidadeDTO> listarParaAvaliacaoHabilidade(FiltroDisciplinaHabilidade filtroDisciplinaHabilidade);
	
}
