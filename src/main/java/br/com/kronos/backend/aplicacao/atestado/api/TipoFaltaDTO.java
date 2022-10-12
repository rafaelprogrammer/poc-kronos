package br.com.kronos.backend.aplicacao.atestado.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.atestado.EnumTipoFalta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoFaltaDTO {

	private int valor;
	private String nome;

	public static List<TipoFaltaDTO> tipos() {
		return Arrays.asList(EnumTipoFalta.values()).stream()
				.map(t -> TipoFaltaDTO.builder().valor(t.valor()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
