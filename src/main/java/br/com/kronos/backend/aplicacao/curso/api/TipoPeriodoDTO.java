package br.com.kronos.backend.aplicacao.curso.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.curso.EnumTipoPeriodo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoPeriodoDTO {

	private int id;
	private String nome;

	public static List<TipoPeriodoDTO> tipos() {
		return Arrays.asList(EnumTipoPeriodo.values()).stream()
				.map(t -> TipoPeriodoDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
