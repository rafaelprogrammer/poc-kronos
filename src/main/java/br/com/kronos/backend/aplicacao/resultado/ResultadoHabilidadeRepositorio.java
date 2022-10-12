package br.com.kronos.backend.aplicacao.resultado;

import java.util.List;

public interface ResultadoHabilidadeRepositorio {
	
	Long criar(ResultadoHabilidade resultadoHabilidade);
	Long alterar(ResultadoHabilidade resultadoHabilidade);
	ResultadoHabilidade buscarPorId(Long id);
	List<ResultadoHabilidade> listar(FiltroResultadoHabilidade filtroResultadoHabilidade);
	boolean excluir(Long id);
	int contar(FiltroResultadoHabilidade filtroResultadoHabilidade);
	boolean excluirPorAvaliadoEAvaliacaoHabilidade(Long idAvaliado, Long idAvaliacaoHabilidade);

}