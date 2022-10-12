package br.com.kronos.backend.aplicacao.empresa;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroTelefoneEmpresa {

	private Long id;
    private String numero;
    private Long idEmpresa;
	private Integer qtdTotal;
	private Integer numeroPagina;
}