package br.com.kronos.backend.aplicacao.arquivo.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.arquivo.EnumTipoArquivo;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TipoArquivoDTO {

	private int id;
	private String nome;
	
	public static List<TipoArquivoDTO> tipos() {
		return Arrays.asList(EnumTipoArquivo.values()).stream()
				.map(t -> TipoArquivoDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}
}
