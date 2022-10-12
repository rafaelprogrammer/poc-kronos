package br.com.kronos.backend.adaptadores.seguranca.servico;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.kronos.backend.adaptadores.seguranca.UserAuthentication;
import br.com.kronos.backend.adaptadores.seguranca.UserNotFoundException;
import br.com.kronos.backend.adaptadores.seguranca.filter.SecurityConstants;
import br.com.kronos.backend.aplicacao.api.dto.UsuarioDTO;
import br.com.kronos.backend.aplicacao.api.impl.UsuarioServico;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class JsonWebTokenAuthenticationService implements TokenAuthenticationService {

	@NonNull
    private String secretKey;

    @NonNull
    private UsuarioServico usuarioServico;
  
    @Override
    public Authentication authenticate(final HttpServletRequest request) {
        final String token = request.getHeader(SecurityConstants.AUTH_HEADER_NAME);
        final Jws<Claims> tokenData = parseToken(token);
        if (tokenData != null) {
            UsuarioDTO user = getUserFromToken(tokenData);
            if (user != null) {
                return new UserAuthentication(user);
            }
        }
        return null;
    }

    private Jws<Claims> parseToken(final String token) {
        if (token != null) {
            try {
                return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
                    | IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }

    private UsuarioDTO getUserFromToken(final Jws<Claims> tokenData) {
        try {
            return (UsuarioDTO) usuarioServico
                    .buscarPorUsuario(tokenData.getBody().get("username").toString());
        } catch (UsernameNotFoundException e) {
            throw new UserNotFoundException("User "
                    + tokenData.getBody().get("username").toString() + " not found");
        } catch (ExcecaoDeNegocio e) {
        	throw new UserNotFoundException("User "
                    + tokenData.getBody().get("username").toString() + " not found");
		}
    }
}
