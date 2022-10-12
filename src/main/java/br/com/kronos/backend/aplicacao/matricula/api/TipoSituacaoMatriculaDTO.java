package br.com.kronos.backend.aplicacao.matricula.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.matricula.EnumTipoSituacaoMatricula;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoSituacaoMatriculaDTO {

	private int id;
	private String nome;

	public static List<TipoSituacaoMatriculaDTO> tipos() {
		return Arrays.asList(EnumTipoSituacaoMatricula.values()).stream()
				.map(t -> TipoSituacaoMatriculaDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
