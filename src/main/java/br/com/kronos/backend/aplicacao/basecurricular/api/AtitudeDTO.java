package br.com.kronos.backend.aplicacao.basecurricular.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AtitudeDTO implements Serializable {

    private static final long serialVersionUID = 1L;   
    private long id;
    private String nome;
    private String codigo;
    private Long idInstituicao;
    private Long idValor;
    private String valor;
    private boolean ativo;
    	
}

