package br.com.kronos.backend.aplicacao.curso;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroCursoDocumento {

	private Long id;  
    private Long idCurso;
    private Long idTipoDocumento;
	private Integer qtdTotal;
	private Integer numeroPagina;
}