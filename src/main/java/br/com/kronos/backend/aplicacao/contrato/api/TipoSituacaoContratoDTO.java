package br.com.kronos.backend.aplicacao.contrato.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.contrato.EnumTipoSituacaoContrato;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoSituacaoContratoDTO {

	private int id;
	private String nome;

	public static List<TipoSituacaoContratoDTO> tipos() {
		return Arrays.asList(EnumTipoSituacaoContrato.values()).stream()
				.map(t -> TipoSituacaoContratoDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
