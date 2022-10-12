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
public class PeriodoExecucaoDTO implements Serializable {

    private static final long serialVersionUID = 1L;      
    private long id; 
    private Long idCalendario;
    private Long idPeriodo;
    private Long idCurso;
    private Integer ano;

    	
}