package br.com.kronos.backend.aplicacao.contrato;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroContratoConvenio {

    private Long idContrato;
    private Long idConvenio;
	private Integer qtdTotal;
	private Integer numeroPagina;
}