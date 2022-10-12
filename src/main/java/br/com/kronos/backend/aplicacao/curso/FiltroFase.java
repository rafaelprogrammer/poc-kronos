package br.com.kronos.backend.aplicacao.curso;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroFase {

    private Long id;  
    private int numero;
    private String nome;
    private String sigla;
    private Long idPeriodo;
    private Long idCurso;
	private Integer qtdTotal;
	private Integer numeroPagina;
}