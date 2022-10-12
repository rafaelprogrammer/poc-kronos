package br.com.kronos.backend.aplicacao.pessoa.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.pessoa.FiltroTalento;

public interface TalentoServico {
	
	void criar(List<TalentoDTO> dtos);
	PaginacaoDTO<TalentoDTO> listar(FiltroTalento filtro);
	boolean excluir(long id, int idTipoTalento);
	List<TalentoDTO> buscarTalentosDaPessoa(long idPessoa);

}