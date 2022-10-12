package br.com.kronos.backend.aplicacao.avaliacao.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.avaliacao.EnumTipoFormato;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoFormatoDTO {

	private int id;
	private String nome;

	public static List<TipoFormatoDTO> tipos() {
		return Arrays.asList(EnumTipoFormato.values()).stream()
				.map(t -> TipoFormatoDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
