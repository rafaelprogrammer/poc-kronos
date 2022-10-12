package br.com.kronos.backend.aplicacao.contrato;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroParcela {

    private Long id;     
    private int numero;
    private Long idContrato;
	private Integer qtdTotal;
	private Integer numeroPagina;
}