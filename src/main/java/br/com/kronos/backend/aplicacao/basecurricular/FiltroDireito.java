package br.com.kronos.backend.aplicacao.basecurricular;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroDireito {

    private Long id;  
    private Integer idNivel;
    private String codigo;
    private Boolean ativo;
    private Boolean bncc;
    private Long idInstituicao;
    private Long idDisciplina;
	private Integer qtdTotal;
	private Integer numeroPagina;
}