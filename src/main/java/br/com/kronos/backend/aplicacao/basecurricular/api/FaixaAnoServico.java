package br.com.kronos.backend.aplicacao.basecurricular.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroFaixaAno;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;

public interface FaixaAnoServico {
	
	PaginacaoDTO<FaixaAnoDTO> listar(FiltroFaixaAno filtro);
	Integer criar(FaixaAnoDTO dto) throws ExcecaoDeNegocio;
	Integer alterar(FaixaAnoDTO dto) throws ExcecaoDeNegocio;
	FaixaAnoDTO buscarPorId(int id);
	boolean excluir(int id);
	List<FaixaAnoDTO> listarParaCombo(FiltroFaixaAno filtro);

}