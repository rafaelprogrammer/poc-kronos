package br.com.kronos.backend.aplicacao.curso.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.curso.FiltroFase;
import br.com.kronos.backend.aplicacao.curso.FiltroSubFase;

public interface SubFaseServico {  
	
	Long criar(SubFaseDTO subFaseDTO);
	Long alterar(SubFaseDTO subFaseDTO);
	PaginacaoDTO<SubFaseDTO>listar(FiltroSubFase filtroSubFase);
	SubFaseDTO buscarPorId(Long id);
	boolean excluir(Long id);
	public List<SubFaseResumoDTO> listarParaCombo(FiltroFase filtroFase);

}