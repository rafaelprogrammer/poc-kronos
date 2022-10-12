package br.com.kronos.backend.aplicacao.pessoa;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroTelefonePessoa {

	private Integer id;
    private Integer numero;
    private Long idPessoa;
	private Integer qtdTotal;
	private Integer numeroPagina;
}