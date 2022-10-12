package br.com.kronos.backend.aplicacao.basecurricular;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroObjetivo {

    private Long id;  
    private Long idCampoExperiencia;
    private String codigo;
    private Boolean ativo;
    private Boolean bncc;
    private Long idInstituicao;
    private Long idDisciplina;
    private Long idFaixaAno;
	private Integer qtdTotal;
	private Integer numeroPagina;
}