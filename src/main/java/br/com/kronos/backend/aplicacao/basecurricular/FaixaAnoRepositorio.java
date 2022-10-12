package br.com.kronos.backend.aplicacao.basecurricular;

import java.util.List;

import br.com.kronos.backend.aplicacao.basecurricular.api.FaixaAnoDTO;

public interface FaixaAnoRepositorio {
	
	List<FaixaAnoDTO> listar(FiltroFaixaAno filtroFaixaAno);
	int contar(FiltroFaixaAno filtroFaixaAno);
	Integer criar(FaixaAno faixa);
	Integer alterar(FaixaAno faixa);
	FaixaAnoDTO buscarPorId(Integer id);
	boolean excluir(Integer id);

}