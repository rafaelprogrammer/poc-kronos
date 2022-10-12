package br.com.kronos.backend.aplicacao.contrato;

import lombok.Builder;
import lombok.Getter;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

@Getter
public class ContratoConvenio {

	private long idContrato;
	private long idConvenio;
	private double percentualDesconto;
	
	@Builder
	public ContratoConvenio(long idContrato, long idConvenio, double percentualDesconto) {
		
		this.idContrato = exigirNaoNulo(idContrato, "Contrato"); 
		this.idConvenio = exigirNaoNulo(idConvenio, "Convenio");
		this.percentualDesconto = exigirNaoNulo(percentualDesconto, "Percentual de desconto");
	}
	
}
