package br.com.kronos.backend.aplicacao.comum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroCidade {

	private Integer id;
	private String nome;
	private Integer qtdTotal;
	private Integer numeroPagina;
	private Integer idEstado;
}
