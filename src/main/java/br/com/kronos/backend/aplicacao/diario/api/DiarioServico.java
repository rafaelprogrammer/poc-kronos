package br.com.kronos.backend.aplicacao.diario.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.diario.FiltroDiario;
import br.com.kronos.backend.aplicacao.diario.api.DiarioDTO;


public interface DiarioServico {
	
	Long criar(DiarioDTO diarioDTO) throws ExcecaoDeNegocio;
	Long alterar(DiarioDTO diarioDTO)throws ExcecaoDeNegocio;
	DiarioDTO buscarPorId(long id);
	PaginacaoDTO<DiarioDTO>listar(FiltroDiario filtroDiario) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	void verificarSeExisteDiario(DiarioDTO diarioDTO);
	
}
