package br.com.kronos.backend.aplicacao.calendario;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroEvento {

    private Long id;  
    private Boolean diaLetivo;
    private Long idCategoriaEvento;
    private Long idCalendario;
	private Integer qtdTotal;
	private Integer numeroPagina;
}