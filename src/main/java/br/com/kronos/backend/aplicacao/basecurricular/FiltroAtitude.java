package br.com.kronos.backend.aplicacao.basecurricular;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroAtitude {

    private Long id;  
    private String nome;
    private String codigo;
    private Long idInstituicao;
    private Long idValor;
    private Boolean ativo;
	private Integer qtdTotal;
	private Integer numeroPagina;
}