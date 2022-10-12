package br.com.kronos.backend.aplicacao.resultado.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AvaliadoDTO implements Serializable {

    private static final long serialVersionUID = 1L;   
    private Long id;
    private Long idAvaliacao;
    private Long idCredito;
    private Long idGrupoAvaliacao;
    private Double nota;
    private Long idMencao; 
    private boolean descarte; 
    private String motivoDescarte; 
    private Integer numeroRegistro;
    private String nomeAvaliado;
    private String[] mencoes;
    private String situacao;
    	
}
