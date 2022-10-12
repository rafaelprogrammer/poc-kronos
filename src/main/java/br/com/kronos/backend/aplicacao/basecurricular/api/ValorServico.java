package br.com.kronos.backend.aplicacao.basecurricular.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroValor;

public interface ValorServico {
	
	Long criar(ValorDTO valorDTO) throws ExcecaoDeNegocio;
	Long alterar(ValorDTO valorDTO)throws ExcecaoDeNegocio;
	ValorDTO buscarPorId(long id);
	PaginacaoDTO<ValorDTO>listar(FiltroValor filtroValor) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	List<ValorDTO> listarParaCombo(FiltroValor build);

}

