package br.com.kronos.backend.aplicacao.pessoa.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.pessoa.FiltroTelefonePessoa;

public interface TelefonePessoaServico {
	
	Integer criar(TelefonePessoaDTO dto);
	PaginacaoDTO<TelefonePessoaDTO> listar(FiltroTelefonePessoa filtro);
	boolean excluir(int id);

}