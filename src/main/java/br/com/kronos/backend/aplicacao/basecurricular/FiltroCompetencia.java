package br.com.kronos.backend.aplicacao.basecurricular;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroCompetencia {

    private Long id;
    private List<Long> ids;
    private Integer idNivel;
    private Long idComponente;
    private Long idDisciplina;
    private String codigo;
    private Boolean ativo;
    private Boolean bncc;
    private Boolean geral;
    private Long idInstituicao;
	private Integer qtdTotal;
	private Integer numeroPagina;
}