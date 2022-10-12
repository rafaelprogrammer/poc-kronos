package br.com.kronos.backend.aplicacao.conselho;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroConselhoPessoa {

    private Long idPessoa;
    private Long idConselho;
	private Integer qtdTotal;
	private Integer numeroPagina;
}

