package br.com.kronos.backend.aplicacao.basecurricular;

import java.util.List;

import br.com.kronos.backend.aplicacao.basecurricular.api.ComponenteDTO;

public interface ComponenteRepositorio {
	
	Long criar(Componente componente);
	Long alterar(Componente componente);
	ComponenteDTO buscarPorId(Long id);
	List<ComponenteDTO> listar(FiltroComponente filtroComponente);
	boolean excluir(Long id);
	int contar(FiltroComponente filtroComponente);

}