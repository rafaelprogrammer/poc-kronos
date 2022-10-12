package br.com.kronos.backend.aplicacao.matricula.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.matricula.EnumTipoCredito;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoCreditoDTO {

	private int id;
	private String nome;

	public static List<TipoCreditoDTO> tipos() {
		return Arrays.asList(EnumTipoCredito.values()).stream()
				.map(t -> TipoCreditoDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
