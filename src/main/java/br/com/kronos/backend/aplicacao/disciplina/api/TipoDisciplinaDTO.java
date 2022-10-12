package br.com.kronos.backend.aplicacao.disciplina.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.disciplina.EnumTipoDisciplina;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoDisciplinaDTO {

	private int id;
	private String nome;

	public static List<TipoDisciplinaDTO> tipos() {
		return Arrays.asList(EnumTipoDisciplina.values()).stream()
				.map(t -> TipoDisciplinaDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
