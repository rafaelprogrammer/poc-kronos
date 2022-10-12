package br.com.kronos.backend.aplicacao.calendario.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.calendario.EnumTipoSituacaoCalendario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoSituacaoCalendarioDTO {

	private int id;
	private String nome;

	public static List<TipoSituacaoCalendarioDTO> tipos() {
		return Arrays.asList(EnumTipoSituacaoCalendario.values()).stream()
				.map(t -> TipoSituacaoCalendarioDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
