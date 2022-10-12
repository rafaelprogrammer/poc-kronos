package br.com.kronos.backend.aplicacao.comum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroPais {

	private Integer id;
	private String nome;
	private String sigla;
}
