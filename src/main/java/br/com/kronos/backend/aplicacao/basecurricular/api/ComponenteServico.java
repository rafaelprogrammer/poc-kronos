package br.com.kronos.backend.aplicacao.basecurricular.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroComponente;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;

public interface ComponenteServico {
	
	Long criar(ComponenteDTO componenteDTO) throws ExcecaoDeNegocio;
	Long alterar(ComponenteDTO componenteDTO)throws ExcecaoDeNegocio;
	ComponenteDTO buscarPorId(long id);
	PaginacaoDTO<ComponenteDTO>listar(FiltroComponente filtroComponente) throws ExcecaoDeNegocio;
	boolean excluir(Long id) throws ExcecaoDeNegocio;
	List<ComponenteDTO> listarParaCombo(FiltroComponente filtro);

}

