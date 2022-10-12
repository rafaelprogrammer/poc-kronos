package br.com.kronos.backend.aplicacao.curso;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroPeriodo {

    private Long id;  
    private Integer numero;
    private String nome;
    private String sigla;
    private Long idCurso;
    private Long idMatricula;
    private Integer anoPeriodoExecucao;
    private Boolean dependenciaContrato;
	private Integer qtdTotal;
	private Integer numeroPagina;
}