package br.com.kronos.backend.aplicacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(of= {"id"})
@AllArgsConstructor
@Builder
@Getter
public class Perfil {

	private Integer id;
	private String nome;
}
