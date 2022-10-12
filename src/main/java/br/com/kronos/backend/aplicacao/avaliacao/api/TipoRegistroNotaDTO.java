package br.com.kronos.backend.aplicacao.avaliacao.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.avaliacao.EnumTipoRegistroNota;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoRegistroNotaDTO {

	private int id;
	private String nome;

	public static List<TipoRegistroNotaDTO> tipos() {
		return Arrays.asList(EnumTipoRegistroNota.values()).stream()
				.map(t -> TipoRegistroNotaDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
