package br.com.kronos.backend.aplicacao.matricula.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.matricula.EnumTipoPrograma;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoProgramaDTO {

	private int id;
	private String nome;

	public static List<TipoProgramaDTO> tipos() {
		return Arrays.asList(EnumTipoPrograma.values()).stream()
				.map(t -> TipoProgramaDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
