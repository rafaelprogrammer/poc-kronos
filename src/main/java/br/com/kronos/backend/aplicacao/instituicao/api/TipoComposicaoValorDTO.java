package br.com.kronos.backend.aplicacao.instituicao.api;

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
public class TipoComposicaoValorDTO {

	private int id;
	private String nome;

	public static List<TipoComposicaoValorDTO> tipos() {
		return Arrays.asList(EnumTipoSituacaoContrato.values()).stream()
				.map(t -> TipoComposicaoValorDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
