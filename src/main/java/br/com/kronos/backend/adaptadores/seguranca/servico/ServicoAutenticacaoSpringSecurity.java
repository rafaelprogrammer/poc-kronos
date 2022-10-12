package br.com.kronos.backend.adaptadores.seguranca.servico;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.kronos.backend.adaptadores.seguranca.UserAuthentication;
import br.com.kronos.backend.aplicacao.api.dto.UsuarioDTO;
import br.com.kronos.backend.aplicacao.comum.ServicoAutenticacao;

public class ServicoAutenticacaoSpringSecurity implements ServicoAutenticacao {

	@Override
	public UsuarioDTO buscarUsuarioLogado() {
		UserAuthentication authentication = getAuthentication();
		return authentication != null ? authentication.getUser() : null;
	}

	private UserAuthentication getAuthentication() {
		UserAuthentication authentication = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		return authentication;
	}

//	@Override
//	public boolean usuarioEhAdmin() {
//		Authentication authentication = getAuthentication();
//		if (authentication == null || authentication.getAuthorities() == null || authentication.getAuthorities().isEmpty()) {
//			return false;
//		}
//		return authentication.getAuthorities().stream()
//				.filter(a -> a.getAuthority().equals(Membro.ADMIN)).findFirst().isPresent();
//	}

}
