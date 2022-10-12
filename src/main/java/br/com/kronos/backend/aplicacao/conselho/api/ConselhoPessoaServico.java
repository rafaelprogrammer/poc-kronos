package br.com.kronos.backend.aplicacao.conselho.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.conselho.FiltroConselhoPessoa;

public interface ConselhoPessoaServico {
	
	void criar(List<ConselhoPessoaDTO> dtos);
	PaginacaoDTO<ConselhoPessoaDTO> listar(FiltroConselhoPessoa filtroConselhoPessoa);
	boolean excluir(long idPessoa, long idConselho);

}
