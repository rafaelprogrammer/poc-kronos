package br.com.kronos.backend.aplicacao.basecurricular.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroObjetivo;

public interface ObjetivoServico {
	
	Long criar(ObjetivoDTO objetivoDTO);
	Long alterar(ObjetivoDTO objetivoDTO);
	ObjetivoDTO buscarPorId(long id);
	PaginacaoDTO<ObjetivoDTO>listar(FiltroObjetivo filtroObjetivo);
	boolean excluir(Long id);
	PaginacaoDTO<ObjetivoDTO> listarParaDisciplinaObjetivo(FiltroObjetivo filtroObjetivo);

}
