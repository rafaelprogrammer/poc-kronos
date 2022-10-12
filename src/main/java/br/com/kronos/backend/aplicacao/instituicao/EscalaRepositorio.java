package br.com.kronos.backend.aplicacao.instituicao;

import java.util.List;

import br.com.kronos.backend.aplicacao.instituicao.api.MensaoLimiteDTO;

public interface EscalaRepositorio {
	
	Long criar(Escala escala);
	Long alterar(Escala escala);
	Escala buscarPorId(Long id);
	List<Escala> listar(FiltroEscala filtroEscala);
	boolean excluir(Long id);
	int contar(FiltroEscala filtroEscala);
	List<MensaoLimiteDTO> listarMensaoELimite(FiltroEscala filtro);
}