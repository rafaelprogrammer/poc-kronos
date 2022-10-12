package br.com.kronos.backend.aplicacao.arquivo.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.arquivo.EnumTipoConteudo;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TipoConteudoDTO {

	private int id;
	private String nome;
	
	public static List<TipoConteudoDTO> tipos() {
		return Arrays.asList(EnumTipoConteudo.values()).stream()
				.map(t -> TipoConteudoDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}
}
