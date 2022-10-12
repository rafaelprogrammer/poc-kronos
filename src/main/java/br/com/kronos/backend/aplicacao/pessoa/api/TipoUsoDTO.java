package br.com.kronos.backend.aplicacao.pessoa.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.pessoa.EnumTipoUso;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TipoUsoDTO {

	private int id;
	private String nome;
	
	public static List<TipoUsoDTO> tipos() {
		return Arrays.asList(EnumTipoUso.values()).stream()
				.map(t -> TipoUsoDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}
}
