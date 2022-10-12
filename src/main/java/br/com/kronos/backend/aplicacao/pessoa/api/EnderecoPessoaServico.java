package br.com.kronos.backend.aplicacao.pessoa.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.pessoa.FiltroEnderecoPessoa;

public interface EnderecoPessoaServico {
	
	Integer criar(EnderecoPessoaDTO enderecoPessoaDTO) throws ExcecaoDeNegocio;
	PaginacaoDTO<EnderecoPessoaDTO>listar(FiltroEnderecoPessoa filtroEnderecoPessoa) throws ExcecaoDeNegocio;
	boolean excluir(Integer id) throws ExcecaoDeNegocio;

}