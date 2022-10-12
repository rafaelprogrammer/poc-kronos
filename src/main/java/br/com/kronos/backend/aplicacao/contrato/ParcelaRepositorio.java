package br.com.kronos.backend.aplicacao.contrato;

import java.util.List;

public interface ParcelaRepositorio {
	
	Long criar(Parcela parcela);
	Long alterar(Parcela parcela);
	Parcela buscarPorId(Long id);
	List<Parcela> listar(FiltroParcela filtroParcela);
	boolean excluir(Long id);
	int contar(FiltroParcela filtroParcela);

}