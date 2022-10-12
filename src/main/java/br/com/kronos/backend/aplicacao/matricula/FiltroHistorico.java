package br.com.kronos.backend.aplicacao.matricula;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroHistorico {

    private Long id;     
    private Long idMatricula;
    private int ano;    
	private Integer qtdTotal;
	private Integer numeroPagina;
}