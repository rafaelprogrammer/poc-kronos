package br.com.kronos.backend.aplicacao.basecurricular.api;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.basecurricular.EnumNivel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NivelDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String nome;

	public static List<NivelDTO> tipos() {
		return Arrays.asList(EnumNivel.values()).stream()
				.map(t -> NivelDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
