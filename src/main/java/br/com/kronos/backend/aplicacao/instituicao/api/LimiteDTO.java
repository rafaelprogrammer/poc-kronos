package br.com.kronos.backend.aplicacao.instituicao.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LimiteDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private int idTipoMencao;  
    private Long idEscala;
    private Double maximo;
    private Double minimo;
    	
}
