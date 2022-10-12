package br.com.kronos.backend.adaptadores.seguranca.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class TokenDTO implements Serializable {

    private static final long serialVersionUID = 6710061358371752955L;

    private String token;

    public TokenDTO() {
    }

}
