package br.com.kronos.backend.adaptadores.seguranca.servico;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.kronos.backend.adaptadores.seguranca.Authority;
import br.com.kronos.backend.adaptadores.seguranca.ServiceException;
import br.com.kronos.backend.adaptadores.seguranca.dto.TokenDTO;
import br.com.kronos.backend.adaptadores.seguranca.dto.UserDTO;
import br.com.kronos.backend.aplicacao.api.dto.UsuarioDTO;
import br.com.kronos.backend.aplicacao.api.impl.UsuarioServico;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class JsonWebTokenService implements TokenService {

    private static int tokenExpirationTime = 30;

    @NonNull
    private String tokenKey;
    
    @NonNull
    private UsuarioServico usuarioServico;
    
    @NonNull
    private PasswordEncoder passwordEncoder;


    @Override
    public TokenDTO getToken(final String username, String password) {
    	try {
        if (username == null || password == null) {
            return null;
        }
        final UsuarioDTO user = (UsuarioDTO) usuarioServico.buscarPorUsuario(username);
        Map<String, Object> tokenData = new HashMap<>();
	        if (passwordEncoder.matches(password, user.getPassword())) {
	            tokenData.put("clientType", "user");
	            tokenData.put("userID", user.getId());
	            tokenData.put("authorities", getMapAuthorities(user.getAuthorities()));
	            tokenData.put("user", UserDTO.builder()
    					.idUsuario(user.getId())
    					.idPessoa(user.getIdPessoa())
    					.idInstituicao(user.getIdInstituicao())
    					.instituicao(user.getInstituicao())
    					.instituicaoMantenedora(user.isInstituicaoMantenedora())
    					.instituicaoAtiva(user.isInstituicaoAtiva())
    					.nome(user.getNome())
    					.build());
	            tokenData.put("username", user.getUsername());
	            tokenData.put("token_create_date", DateUtil.dataHoraAtual());
	            Calendar calendar = Calendar.getInstance();
	            calendar.add(Calendar.MINUTE, tokenExpirationTime);
	            tokenData.put("token_expiration_date", calendar.getTime());
	            JwtBuilder jwtBuilder = Jwts.builder();
	            jwtBuilder.setExpiration(calendar.getTime());
	            jwtBuilder.setClaims(tokenData);
	            return TokenDTO.builder()
	            			.token(jwtBuilder.signWith(SignatureAlgorithm.HS512, tokenKey).compact())
	            			.build();
	
	        } else {
	            throw new ServiceException("Authentication error", this.getClass().getName());
	        }
    	} catch (ExcecaoDeNegocio e) {
    		throw new ServiceException("Authentication error", this.getClass().getName());
		}
    }
    
    public Map<String, String[]> getMapAuthorities(List<Authority> authorities) {
    	return authorities.stream().collect(Collectors.toMap(Authority::name, Authority::menus));
    }

    public static void setTokenExpirationTime(final int tokenExpirationTime) {
        JsonWebTokenService.tokenExpirationTime = tokenExpirationTime;
    }
}
