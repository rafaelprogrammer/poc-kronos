package br.com.kronos.backend.aplicacao.instituicao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroMencao {

	private Long id;  
    private String nome;
    private String simbolo;
	private Integer qtdTotal;
	private Integer numeroPagina;
}