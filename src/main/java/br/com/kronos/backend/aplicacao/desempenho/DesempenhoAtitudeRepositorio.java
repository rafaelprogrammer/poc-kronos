package br.com.kronos.backend.aplicacao.desempenho;

import java.util.List;

public interface DesempenhoAtitudeRepositorio {
    
    int contarPorIdDesempenhoEAtitude(FiltroDesempenhoAtitude filtroDesempenhoAtitude);
	int criar(DesempenhoAtitude desempenhoAtitude);
	List<DesempenhoAtitude> listar(FiltroDesempenhoAtitude filtroDesempenhoAtitude);
	int contar(FiltroDesempenhoAtitude filtroDesempenhoAtitude);
	boolean excluir(long idCurso, long idEscala);

}
