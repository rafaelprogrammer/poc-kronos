package br.com.kronos.backend.aplicacao.resultado.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ResultadoFaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Double notaFinalNormal; 
    private Double notaFinalRecuperacao; 
    private Integer totalAusencia; 
    private Double percentualAusencia; 
    private Long idFaseExecucao;
    private Long idCredito;
    private Long idMencao;
    private Double notaFinal;
    	
}

