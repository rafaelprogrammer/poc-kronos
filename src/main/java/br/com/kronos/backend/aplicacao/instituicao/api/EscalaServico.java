package br.com.kronos.backend.aplicacao.instituicao.api;

import java.util.List;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.instituicao.FiltroEscala;

public interface EscalaServico {
	
	Long criar(EscalaDTO escalaDTO);
	Long alterar(EscalaDTO escalaDTO);
	EscalaDTO buscarPorId(long id);
	PaginacaoDTO<EscalaDTO>listar(FiltroEscala filtroEscala);
	boolean excluir(Long id);
	List<MensaoLimiteDTO> listarMensaoELimite(FiltroEscala filtro);
	
}