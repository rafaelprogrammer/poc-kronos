package br.com.kronos.backend.aplicacao.contrato.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.contrato.EnumTipoFormaPagamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoFormaPagamentoDTO {

	private int id;
	private String nome;

	public static List<TipoFormaPagamentoDTO> tipos() {
		return Arrays.asList(EnumTipoFormaPagamento.values()).stream()
				.map(t -> TipoFormaPagamentoDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
