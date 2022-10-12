package br.com.kronos.backend.aplicacao.curso.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.curso.EnumTipoRegimeLetivo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoRegimeLetivoDTO {

	private int id;
	private String nome;

	public static List<TipoRegimeLetivoDTO> tipos() {
		return Arrays.asList(EnumTipoRegimeLetivo.values()).stream()
				.map(t -> TipoRegimeLetivoDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
