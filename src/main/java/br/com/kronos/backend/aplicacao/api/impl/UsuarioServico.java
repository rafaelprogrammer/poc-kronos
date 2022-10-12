package br.com.kronos.backend.aplicacao.api.impl;

import br.com.kronos.backend.aplicacao.FiltroUsuario;
import br.com.kronos.backend.aplicacao.api.dto.UsuarioDTO;
import br.com.kronos.backend.aplicacao.api.dto.UsuarioPessoaDTO;
import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;

public interface UsuarioServico {
	
	Long criar(UsuarioDTO usuario) throws ExcecaoDeNegocio;
	Long alterar(UsuarioDTO usuario) throws ExcecaoDeNegocio;
	UsuarioDTO buscarPorId(Long id) throws ExcecaoDeNegocio;
	UsuarioDTO buscarPorUsuario(String username) throws ExcecaoDeNegocio;
	PaginacaoDTO<UsuarioPessoaDTO> listar(FiltroUsuario filtroUsuario) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;

}
