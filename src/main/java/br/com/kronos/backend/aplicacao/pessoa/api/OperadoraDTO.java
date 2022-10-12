package br.com.kronos.backend.aplicacao.pessoa.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.pessoa.api.OperadoraDTO;
import br.com.kronos.backend.aplicacao.pessoa.EnumOperadora;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OperadoraDTO {

	private int id;
	private String nome;
	
	public static List<OperadoraDTO> tipos() {
		return Arrays.asList(EnumOperadora.values()).stream()
				.map(t -> OperadoraDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}
}
