package br.com.kronos.backend.aplicacao.curso.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.curso.EnumTipoTurno;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoTurnoDTO {

	private int id;
	private String nome;

	public static List<TipoTurnoDTO> tipos() {
		return Arrays.asList(EnumTipoTurno.values()).stream()
				.map(t -> TipoTurnoDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
