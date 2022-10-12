package br.com.kronos.backend.aplicacao.conselho;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroConselho{

    private Long id;  
    private Long idInstituicao; 
    private LocalDate dataInicioVigencia;
	private Integer qtdTotal;
	private Integer numeroPagina;
}