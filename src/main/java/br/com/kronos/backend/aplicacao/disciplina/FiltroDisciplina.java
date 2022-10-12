package br.com.kronos.backend.aplicacao.disciplina;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
@Builder
public class FiltroDisciplina {

    private Long id;  
    private Integer codigo;
    private String nome;
    private String sigla;
    private String anoTurma;
    private Long idPeriodo;
    private Long idPeriodoExecucao;
    private Long idContrato;
    private Long idMatricula;
	private Integer qtdTotal;
	private Integer numeroPagina;
}