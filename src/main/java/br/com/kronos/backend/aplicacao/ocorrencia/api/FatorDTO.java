package br.com.kronos.backend.aplicacao.ocorrencia.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.ocorrencia.EnumFator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class FatorDTO {

	private int valor;
	private String nome;

	public static List<FatorDTO> fatores() {
		return Arrays.asList(EnumFator.values()).stream()
				.map(t -> FatorDTO.builder().valor(t.valor()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
