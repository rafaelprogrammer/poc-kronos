package br.com.kronos.backend.aplicacao.desempenho;

import java.util.List;

public interface DesempenhoAvaliacaoRepositorio {
    
    int contarPorIdDesempenhoEAvaliacao(FiltroDesempenhoAvaliacao filtroDesempenhoAvaliacao);
	int criar(DesempenhoAvaliacao desempenhoAvaliacao);
	List<DesempenhoAvaliacao> listar(FiltroDesempenhoAvaliacao filtroDesempenhoAvaliacao);
	int contar(FiltroDesempenhoAvaliacao filtroDesempenhoAvaliacao);
	boolean excluir(long idCurso, long idEscala);

}

