package br.com.kronos.backend.aplicacao.basecurricular;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroComponente {

	private Long id;  
    private String nome;
    private Long idInstituicao;
    private Boolean ativo;
    private Boolean bncc;
    private Integer idNivel; 
	private Integer qtdTotal;
	private Integer numeroPagina;
}