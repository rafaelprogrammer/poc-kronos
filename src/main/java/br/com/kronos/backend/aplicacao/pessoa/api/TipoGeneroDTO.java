package br.com.kronos.backend.aplicacao.pessoa.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.pessoa.EnumTipoGenero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoGeneroDTO {

	private int id;
	private String nome;
	private String sigla;

	public static List<TipoGeneroDTO> tipos() {
		return Arrays.asList(EnumTipoGenero.values()).stream()
				.map(t -> TipoGeneroDTO.builder().id(t.id()).nome(t.label()).sigla(t.sigla()).build())
				.collect(Collectors.toList());
	}

}
