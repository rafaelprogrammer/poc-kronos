package br.com.kronos.backend.aplicacao.basecurricular;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroHabilidade {

    private Long id;  
    private Long idComponente;
    private Long idDisciplina;
    private String codigo;
    private Boolean ativo;
    private Boolean bncc;
    private Integer idNivel;
    private Long idInstituicao;
	private Integer qtdTotal;
	private Integer numeroPagina;
}