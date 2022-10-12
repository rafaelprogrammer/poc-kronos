package br.com.kronos.backend.aplicacao.api.impl.comum;

import br.com.kronos.backend.aplicacao.api.dto.comum.CidadeDTO;
import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.comum.FiltroCidade;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;

public interface CidadeServico {
	
	PaginacaoDTO<CidadeDTO> listar(FiltroCidade filtroCidade) throws ExcecaoDeNegocio;

}
