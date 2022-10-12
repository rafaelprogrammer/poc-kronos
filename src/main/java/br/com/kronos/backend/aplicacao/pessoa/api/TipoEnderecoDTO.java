package br.com.kronos.backend.aplicacao.pessoa.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.pessoa.EnumTipoEndereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoEnderecoDTO {

	private int id;
	private String nome;

	public static List<TipoEnderecoDTO> tipos() {
		return Arrays.asList(EnumTipoEndereco.values()).stream()
				.map(t -> TipoEnderecoDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
