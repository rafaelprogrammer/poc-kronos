package br.com.kronos.backend.aplicacao.curso.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.curso.EnumTipoPortaria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoPortariaDTO {

	private int id;
	private String nome;

	public static List<TipoPortariaDTO> tipos() {
		return Arrays.asList(EnumTipoPortaria.values()).stream()
				.map(t -> TipoPortariaDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
