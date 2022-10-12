package br.com.kronos.backend.aplicacao.matricula;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroMatriculaDocumento {

    private Long id;     
    private Long idMatricula;
    private Long idDocumento;
	private Integer qtdTotal;
	private Integer numeroPagina;
}