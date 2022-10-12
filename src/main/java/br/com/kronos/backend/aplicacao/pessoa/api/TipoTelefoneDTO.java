package br.com.kronos.backend.aplicacao.pessoa.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.pessoa.EnumTipoTelefone;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TipoTelefoneDTO {

	private int id;
	private String nome;
	
	public static List<TipoTelefoneDTO> tipos() {
		return Arrays.asList(EnumTipoTelefone.values()).stream()
				.map(t -> TipoTelefoneDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}
}
