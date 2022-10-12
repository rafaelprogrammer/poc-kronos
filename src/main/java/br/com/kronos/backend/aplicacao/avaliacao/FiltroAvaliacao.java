package br.com.kronos.backend.aplicacao.avaliacao;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
@Builder
public class FiltroAvaliacao{

    private Long id;  
    private Long idAtividade;
    private Long idTipoAvaliacao;
    private Long idTurma;
    private Long idDisciplina;
	private Integer qtdTotal;
	private Integer numeroPagina;
}