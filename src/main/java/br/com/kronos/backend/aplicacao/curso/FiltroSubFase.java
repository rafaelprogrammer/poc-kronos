package br.com.kronos.backend.aplicacao.curso;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroSubFase {

    private Long id;  
    private Integer numero;
    private String nome;
    private String sigla;
    private Long idFase;
	private Integer qtdTotal;
	private Integer numeroPagina;
}