package br.com.kronos.backend.aplicacao.matricula.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.matricula.FiltroComprovante;
import br.com.kronos.backend.aplicacao.matricula.api.ComprovanteDTO;


public interface ComprovanteServico {
	
	Long criar(ComprovanteDTO comprovanteDTO) throws ExcecaoDeNegocio;
	Long alterar(ComprovanteDTO comprovanteDTO)throws ExcecaoDeNegocio;
	ComprovanteDTO buscarPorId(long id);
	PaginacaoDTO<ComprovanteDTO>listar(FiltroComprovante filtroComprovante) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	
}