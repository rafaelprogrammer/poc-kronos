package br.com.kronos.backend.aplicacao.basecurricular;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroNivel {

	private Integer id;  
    private String nome;
	private Integer qtdTotal;
	private Integer numeroPagina;
}