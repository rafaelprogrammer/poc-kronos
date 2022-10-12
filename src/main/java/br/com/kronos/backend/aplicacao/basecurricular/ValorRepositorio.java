package br.com.kronos.backend.aplicacao.basecurricular;

import java.util.List;

import br.com.kronos.backend.aplicacao.calendario.FiltroCalendario;

public interface ValorRepositorio {
	
	Long criar(Valor habilidade);
	Long alterar(Valor habilidade);
	Valor buscarPorId(Long id);
	List<Valor> listar(FiltroValor filtroValor);
	boolean excluir(Long id);
	int contar(FiltroValor filtroValor);

}