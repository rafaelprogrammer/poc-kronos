package br.com.kronos.backend.aplicacao.calendario.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CategoriaEventoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String nome;
    private String cor;
    	
}