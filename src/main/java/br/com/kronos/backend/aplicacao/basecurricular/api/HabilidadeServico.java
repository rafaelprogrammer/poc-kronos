package br.com.kronos.backend.aplicacao.basecurricular.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroHabilidade;

public interface HabilidadeServico {
	
	Long criar(HabilidadeDTO habilidadeDTO);
	Long alterar(HabilidadeDTO habilidadeDTO);
	HabilidadeDTO buscarPorId(long id);
	PaginacaoDTO<HabilidadeDTO>listar(FiltroHabilidade filtroHabilidade);
	boolean excluir(Long id);
	PaginacaoDTO<HabilidadeDTO> listarParaDisciplinaHabilidade(FiltroHabilidade filtroHabilidade);

}

