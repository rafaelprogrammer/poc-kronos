package br.com.kronos.backend.adaptadores.seguranca.servico;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.kronos.backend.aplicacao.api.dto.UsuarioDTO;
import br.com.kronos.backend.aplicacao.api.impl.UsuarioServico;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.NonNull;


public class BasicUserDetailsService implements UserDetailsService {

	@NonNull
    private UsuarioServico usuarioServico;


    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		try {
			UsuarioDTO user = usuarioServico.buscarPorUsuario(username);
			if (user != null) {
	            return user;
	        } else {
	            throw new UsernameNotFoundException("User with username:" + username + " not found");
	        }
		} catch (ExcecaoDeNegocio e) {
			throw new UsernameNotFoundException("Erro ao tentar logar no sistema", e);
		}
        
    }
}
