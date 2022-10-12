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
public class ResultadoSubFaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Double notaFinalNormal; 
    private Double notaFinalRecuperacao; 
    private Integer totalAusencia; 
    private Double percentualAusencia; 
    private Long idSubFaseExecucao;
    private Long idCredito;
    private Long idMencao;
    private String mencao;
    private Double notaFinal;
    private Integer numeroRegistro;
    private String nomeAluno;
    private Integer numeroHabilidadesAvaliadas;
    private Integer numeroHabilidadesRecuperadas;
    private Long[] idsCreditos;
    private String situacao;
    	
}

