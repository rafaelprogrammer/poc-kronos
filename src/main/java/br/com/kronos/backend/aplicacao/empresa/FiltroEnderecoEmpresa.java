package br.com.kronos.backend.aplicacao.empresa;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroEnderecoEmpresa {

	private Integer id;
	private String cep;
	private Integer qtdTotal;
	private Integer numeroPagina;
}