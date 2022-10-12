package br.com.kronos.backend.aplicacao.pessoa.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.pessoa.EnumTipoTitulo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoTituloDTO {

	private int id;
	private String nome;

	public static List<TipoTituloDTO> tipos() {
		return Arrays.asList(EnumTipoTitulo.values()).stream()
				.map(t -> TipoTituloDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
