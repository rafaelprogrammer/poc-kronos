package br.com.kronos.backend.aplicacao.basecurricular;

import java.util.List;

import br.com.kronos.backend.aplicacao.basecurricular.api.AtitudeDTO;

public interface AtitudeRepositorio {
	
	Long criar(Atitude atitude);
	Long alterar(Atitude Aatitude);
	AtitudeDTO buscarPorId(Long id);
	List<AtitudeDTO> listar(FiltroAtitude filtroAtitude);
	boolean excluir(Long id);
	int contar(FiltroAtitude filtroAtitude);

}