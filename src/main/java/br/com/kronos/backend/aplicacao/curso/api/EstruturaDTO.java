package br.com.kronos.backend.aplicacao.curso.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(Include.NON_EMPTY)
@NoArgsConstructor
@Data
public class EstruturaDTO {

	private Long id;
	private String nome;
	private List<EstruturaDTO> filhos;
}
