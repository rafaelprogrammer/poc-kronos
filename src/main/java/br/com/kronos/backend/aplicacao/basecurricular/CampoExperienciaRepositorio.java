package br.com.kronos.backend.aplicacao.basecurricular;

import java.util.List;

import br.com.kronos.backend.aplicacao.basecurricular.api.CampoExperienciaDTO;

public interface CampoExperienciaRepositorio {
	
	Long criar(CampoExperiencia campoExperiencia);
	Long alterar(CampoExperiencia campoExperiencia);
	CampoExperienciaDTO buscarPorId(Long id);
	List<CampoExperienciaDTO> listar(FiltroCampoExperiencia filtroCampoExperiencia);
	boolean excluir(Long id);
	int contar(FiltroCampoExperiencia filtroCampoExperiencia);

}