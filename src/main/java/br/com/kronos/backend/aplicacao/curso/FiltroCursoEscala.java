package br.com.kronos.backend.aplicacao.curso;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroCursoEscala {

    private Long idCurso;
    private Long idEscala;
	private Integer qtdTotal;
	private Integer numeroPagina;
}