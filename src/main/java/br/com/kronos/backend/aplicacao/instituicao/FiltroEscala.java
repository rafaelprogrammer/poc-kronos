package br.com.kronos.backend.aplicacao.instituicao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroEscala {

	private Long id;  
    private String nome;
    private Long idCurso;
    private Long idInstituicao;
    private Long idTipoAbrangencia;  
    private Long idTipoAvaliacao;
	private Integer qtdTotal;
	private Integer numeroPagina;
}