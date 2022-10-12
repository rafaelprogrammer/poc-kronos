package br.com.kronos.backend.aplicacao.pessoa.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.pessoa.EnumTipoTalento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoTalentoDTO {

	private int id;
	private String nome;

	public static List<TipoTalentoDTO> tipos() {
		return Arrays.asList(EnumTipoTalento.values()).stream()
				.map(t -> TipoTalentoDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
