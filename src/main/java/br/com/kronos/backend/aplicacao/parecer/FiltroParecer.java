package br.com.kronos.backend.aplicacao.parecer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroParecer{

    private Long id;  
    private Long idSubFaseExecucao; 
    private Long idMatricula;
    private Long idTurma;
	private Integer qtdTotal;
	private Integer numeroPagina;
}