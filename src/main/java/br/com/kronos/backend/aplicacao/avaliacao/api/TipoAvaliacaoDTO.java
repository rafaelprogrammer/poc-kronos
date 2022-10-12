package br.com.kronos.backend.aplicacao.avaliacao.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.avaliacao.EnumTipoAvaliacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoAvaliacaoDTO {

	private int id;
	private String nome;

	public static List<TipoAvaliacaoDTO> tipos() {
		return Arrays.asList(EnumTipoAvaliacao.values()).stream()
				.map(t -> TipoAvaliacaoDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
