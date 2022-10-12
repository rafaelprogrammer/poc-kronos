package br.com.kronos.backend.aplicacao.avaliacao.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GrupoAvaliacaoDTO implements Serializable {

    private static final long serialVersionUID = 1L;      
    private long id;
    private int numero;
    private Long idAvaliacao;
    private String tema;
    private Integer qtdAvaliados;
    	
}
