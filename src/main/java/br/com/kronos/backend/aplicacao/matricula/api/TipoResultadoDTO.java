package br.com.kronos.backend.aplicacao.matricula.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.matricula.EnumTipoResultado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoResultadoDTO {

	private int id;
	private String nome;

	public static List<TipoResultadoDTO> tipos() {
		return Arrays.asList(EnumTipoResultado.values()).stream()
				.map(t -> TipoResultadoDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
