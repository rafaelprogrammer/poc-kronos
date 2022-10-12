package br.com.kronos.backend.adaptadores.seguranca.servico;

import br.com.kronos.backend.adaptadores.seguranca.dto.TokenDTO;

public interface TokenService {

    TokenDTO getToken(String username, String password);
}
