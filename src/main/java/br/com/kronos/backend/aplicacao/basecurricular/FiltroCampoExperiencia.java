package br.com.kronos.backend.aplicacao.basecurricular;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroCampoExperiencia {

	private Long id;  
    private String nome;
    private Integer idNivel; 
	private Integer qtdTotal;
	private Integer numeroPagina;
}