package br.com.kronos.backend.aplicacao.api.impl.comum;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaisDTO;
import br.com.kronos.backend.aplicacao.comum.FiltroPais;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;

public interface PaisServico {
	
	List<PaisDTO> listar(FiltroPais filtroPais) throws ExcecaoDeNegocio;

}
