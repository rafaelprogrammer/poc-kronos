package br.com.kronos.backend.aplicacao.basecurricular.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroCampoExperiencia;

public interface CampoExperienciaServico {
	
	Long criar(CampoExperienciaDTO campoExperienciaDTO) throws ExcecaoDeNegocio;
	Long alterar(CampoExperienciaDTO campoExperienciaDTO)throws ExcecaoDeNegocio;
	CampoExperienciaDTO buscarPorId(long id);
	PaginacaoDTO<CampoExperienciaDTO>listar(FiltroCampoExperiencia filtroCampoExperiencia) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	List<CampoExperienciaDTO> listarParaCombo(FiltroCampoExperiencia filtro);

}