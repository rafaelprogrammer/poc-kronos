package br.com.kronos.backend.aplicacao.atestado.api;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.atestado.FiltroAtestado;

public interface AtestadoServico {
	
	Long criar(AtestadoDTO dto);
	Long alterar(AtestadoDTO dto);
	AtestadoDTO buscarPorId(Long id);
	PaginacaoDTO<AtestadoDTO> listar(FiltroAtestado filtro);
	boolean excluir(Long id);
	void alterarArquivo(AtestadoDTO dto);

}