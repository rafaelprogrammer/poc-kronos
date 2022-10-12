package br.com.kronos.backend.aplicacao.comum;

import br.com.kronos.backend.aplicacao.api.dto.UsuarioDTO;

public interface ServicoAutenticacao {
	
	UsuarioDTO buscarUsuarioLogado();
}
