package br.com.kronos.backend.aplicacao.calendario;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroCalendario {

    private Long id;  
    private Integer numero;
    private Integer ano;
    private Long idInstituicao;
    private Integer idTipoSituacaoCalendario;
    private String descricao;
	private Integer qtdTotal;
	private Integer numeroPagina;
}