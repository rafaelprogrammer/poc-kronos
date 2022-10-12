package br.com.kronos.backend.aplicacao.calendario;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroCategoriaEvento {

    private Long id;  
    private String nome;
    private String cor;
	private Integer qtdTotal;
	private Integer numeroPagina;
}