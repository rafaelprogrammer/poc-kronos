package br.com.kronos.backend.aplicacao.pessoa.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.pessoa.FiltroTitulacao;

public interface TitulacaoServico {
	
	int criar(TitulacaoDTO dto);
	int alterar(TitulacaoDTO dto);
	void alterarArquivo(TitulacaoDTO dto);
	TitulacaoDTO buscarPorId(int id);
	PaginacaoDTO<TitulacaoDTO> listar(FiltroTitulacao filtroTitulacao);
	boolean excluir(int id);

}