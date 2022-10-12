package br.com.kronos.backend.aplicacao.pessoa.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.pessoa.EnumTipoResponsavel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoResponsavelDTO {

	private int id;
	private String nome;

	public static List<TipoResponsavelDTO> tipos() {
		return Arrays.asList(EnumTipoResponsavel.values()).stream()
				.map(t -> TipoResponsavelDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
