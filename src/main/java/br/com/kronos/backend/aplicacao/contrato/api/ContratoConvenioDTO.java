package br.com.kronos.backend.aplicacao.contrato.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ContratoConvenioDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
    private long idContrato;
    private long idConvenio;
    private Double percentualDesconto;
    private boolean criacao;
    
    // public String getNomeEmpresaConvenio() {
    // 	return ....
    // }
	
}