package br.com.kronos.backend.aplicacao.curso.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.funcionario.EnumTipoFuncao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoFuncaoDTO {

	private int id;
	private String nome;

	public static List<TipoFuncaoDTO> tipos() {
		return Arrays.asList(EnumTipoFuncao.values()).stream()
				.map(t -> TipoFuncaoDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
