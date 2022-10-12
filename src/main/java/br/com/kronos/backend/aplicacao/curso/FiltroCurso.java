package br.com.kronos.backend.aplicacao.curso;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
@Builder
public class FiltroCurso {

	private Long id;  
    private String nome;
    private Long idNivel;
    private Long idTipoTurno;
    private Long idInstituicao;
	private Integer qtdTotal;
	private Integer numeroPagina;
}