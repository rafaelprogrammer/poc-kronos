package br.com.kronos.backend.aplicacao.funcionario.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.kronos.backend.aplicacao.funcionario.EnumTipoCategoriaFuncao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TipoCategoriaFuncaoDTO {

	private int id;
	private String nome;

	public static List<TipoCategoriaFuncaoDTO> tipos() {
		return Arrays.asList(EnumTipoCategoriaFuncao.values()).stream()
				.map(t -> TipoCategoriaFuncaoDTO.builder().id(t.id()).nome(t.label()).build())
				.collect(Collectors.toList());
	}

}
