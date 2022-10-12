package br.com.kronos.backend.aplicacao.curso.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TipoMatrizHorarioDTO implements Serializable { 

    private static final long serialVersionUID = 1L;
    private long id;
    private String nome;
    	
}
