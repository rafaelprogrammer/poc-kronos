package br.com.kronos.backend.aplicacao.desempenho.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.desempenho.FiltroDesempenhoAvaliacao;

public interface DesempenhoAvaliacaoServico {
	
	void criar(List<DesempenhoAvaliacaoDTO> dtos);
	PaginacaoDTO<DesempenhoAvaliacaoDTO> listar(FiltroDesempenhoAvaliacao filtroDesempenhoAvaliacao);
	boolean excluir(long idDesempenho, long idAvaliacao);

}

