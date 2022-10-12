package br.com.kronos.backend.aplicacao.resultado.api;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ResultadoHabilidadeDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long idAvaliado;
    private Long idAvaliacaoHabilidade;
    private Double nota;
    private Long idMencao; 
    private boolean descarte; 
    private String motivoDescarte;
    private List<Long> idsAvaliados;
    private List<Long> idsMencoes;
    	
}

