package br.com.kronos.backend.aplicacao.pessoa.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.pessoa.FiltroFiliacao;

public interface FiliacaoServico {
	
	int criar(FiliacaoDTO dto);
	PaginacaoDTO<FiliacaoDTO> listar(FiltroFiliacao filtro);
	boolean excluir(int id);

}