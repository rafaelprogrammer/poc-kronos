package br.com.kronos.backend.aplicacao.desempenho.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.desempenho.FiltroDesempenhoAtitude;

public interface DesempenhoAtitudeServico {
	
	void criar(List<DesempenhoAtitudeDTO> dtos);
	PaginacaoDTO<DesempenhoAtitudeDTO> listar(FiltroDesempenhoAtitude filtroDesempenhoAtitude);
	boolean excluir(long idDesempenho, long idAtitude);

}

