package br.com.kronos.backend.aplicacao.pessoa.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.pessoa.EnumTipoFiliacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoFiliacaoDTO {

	private int id;
	private String nome;

	public static List<TipoFiliacaoDTO> tipos() {
		return Arrays.asList(EnumTipoFiliacao.values()).stream()
				.map(t -> TipoFiliacaoDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
