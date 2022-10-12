package br.com.kronos.backend.aplicacao.avaliacao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroGrupoAvaliacao{

    private Long id;  
    private Integer numero;
    private Long idAvaliacao;
	private Integer qtdTotal;
	private Integer numeroPagina;
}