package br.com.kronos.backend.aplicacao.contrato.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.contrato.FiltroParcela;

public interface ParcelaServico {
	
	Long criar(ParcelaDTO parcelaDTO);
	Long alterar(ParcelaDTO parcelaDTO);
	ParcelaDTO buscarPorId(long id);
	List<ParcelaDTO> listar(FiltroParcela filtroParcela);
	boolean excluir(long id);
	void gerarParcelas(ParcelaDTO parcelaDTO);

}