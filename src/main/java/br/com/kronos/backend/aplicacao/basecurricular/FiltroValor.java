package br.com.kronos.backend.aplicacao.basecurricular;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroValor {

    private Long id;  
    private String nome;
    private Boolean ativo;
    private String codigo;
    private Long idInstituicao;
	private Integer qtdTotal;
	private Integer numeroPagina;
}