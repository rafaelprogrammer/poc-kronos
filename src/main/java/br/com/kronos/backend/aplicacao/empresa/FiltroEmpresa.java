package br.com.kronos.backend.aplicacao.empresa;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroEmpresa {

	private Integer id;
	private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
	private Integer qtdTotal;
	private Integer numeroPagina;
}
