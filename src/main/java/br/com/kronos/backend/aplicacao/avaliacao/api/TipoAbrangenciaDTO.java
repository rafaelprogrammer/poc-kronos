package br.com.kronos.backend.aplicacao.avaliacao.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.avaliacao.EnumTipoAbrangencia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoAbrangenciaDTO {

	private int id;
	private String nome;

	public static List<TipoAbrangenciaDTO> tipos() {
		return Arrays.asList(EnumTipoAbrangencia.values()).stream()
				.map(t -> TipoAbrangenciaDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
