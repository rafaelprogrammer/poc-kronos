package br.com.kronos.backend.aplicacao.pessoa.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.pessoa.EnumTipoDocumento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoDocumentoDTO {

	private int id;
	private String nome;

	public static List<TipoDocumentoDTO> tipos() {
		return Arrays.asList(EnumTipoDocumento.values()).stream()
				.map(t -> TipoDocumentoDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
