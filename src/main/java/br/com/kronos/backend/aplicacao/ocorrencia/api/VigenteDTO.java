package br.com.kronos.backend.aplicacao.ocorrencia.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.ocorrencia.EnumVigente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class VigenteDTO {

	private String valor;
	private String nome;

	public static List<VigenteDTO> fatores() {
		return Arrays.asList(EnumVigente.values()).stream()
				.map(t -> VigenteDTO.builder().valor(t.name()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
