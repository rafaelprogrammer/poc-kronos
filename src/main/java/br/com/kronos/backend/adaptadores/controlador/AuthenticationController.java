package br.com.kronos.backend.adaptadores.controlador;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.kronos.backend.adaptadores.seguranca.dto.LoginDTO;
import br.com.kronos.backend.adaptadores.seguranca.dto.TokenDTO;
import br.com.kronos.backend.adaptadores.seguranca.servico.TokenService;
import lombok.AllArgsConstructor;
import lombok.NonNull;


@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthenticationController extends BaseControlador {

	@NonNull
    private TokenService tokenService;


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> authenticate(@RequestBody final LoginDTO dto) {
        final TokenDTO token = tokenService.getToken(dto.getUsername(), dto.getPassword());
        if (token != null) {
//            final TokenDTO response = new TokenDTO();
//            response.setToken(token);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Authentication failed", HttpStatus.BAD_REQUEST);
        }
    }
}
