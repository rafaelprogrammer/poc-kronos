package br.com.kronos.backend.aplicacao.disciplina.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DisciplinaCreditoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private Boolean obrigatoria;
    private String nome;
    private Integer cargaHorariaTotal;
    private Integer cargaHorariaPresencial;
    private Integer cargaHorariaDistancia;
    private Double valor;
    private String siglaPeriodo;
    	
}
