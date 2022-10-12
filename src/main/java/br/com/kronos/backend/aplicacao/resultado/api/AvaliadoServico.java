package br.com.kronos.backend.aplicacao.resultado.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.resultado.FiltroAvaliado;


public interface AvaliadoServico {
	
	Long criar(AvaliadoDTO avaliadoDTO);
	Long alterar(AvaliadoDTO avaliadoDTO);
	AvaliadoDTO buscarPorId(long id);
	PaginacaoDTO<AvaliadoDTO>listar(FiltroAvaliado filtroAvaliado);
	boolean excluir(Long id);
	public List<AvaliadoDTO> listarParaSelecaoNaAvaliacao(FiltroAvaliado filtroAvaliado);
	void criarAvaliados(List<AvaliadoDTO> dtos);
	public List<AvaliadoDTO> listarResultadoHabiliadeDoAvaliado(FiltroAvaliado filtroAvaliado);
	
}

