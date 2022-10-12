package br.com.kronos.backend.aplicacao.api.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(of= {"id"})
@AllArgsConstructor
@Builder
@Getter
public class PerfilDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;
	
}
