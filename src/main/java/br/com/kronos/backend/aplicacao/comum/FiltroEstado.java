package br.com.kronos.backend.aplicacao.comum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroEstado {

	private Integer id;
	private String nome;
	private String sigla;
	private Integer qtdTotal;
	private Integer numeroPagina;
	private Integer idPais;
}
