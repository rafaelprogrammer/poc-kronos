package br.com.kronos.backend.aplicacao.desempenho;

import java.util.List;

public interface DesempenhoRepositorio {
	
	Long criar(Desempenho desempenho);
	Long alterar(Desempenho desempenho);
	Desempenho buscarPorId(Long id);
	List<Desempenho> listar(FiltroDesempenho filtroDesempenho);
	boolean excluir(Long id);
	int contar(FiltroDesempenho filtroDesempenho);
		
	void atribuirAtitudeDesempenho(DesempenhoAtitude desempenhoAtitude);
	void excluirAtitudeDesempenho(DesempenhoAtitude desempenhoAtitude);
	boolean verificarVinculoDesempenhoAtitude(FiltroDesempenhoAtitude filtroDesempenhoAtitude);
	
	void atribuirAvaliacaoDesempenho(DesempenhoAvaliacao desempenhoAvaliacao);
	void excluirAvaliacaoDesempenho(DesempenhoAvaliacao desempenhoAvaliacao);
	boolean verificarVinculoDesempenhoAvaliacao(FiltroDesempenhoAvaliacao filtroDesempenhoAvaliacao);

}