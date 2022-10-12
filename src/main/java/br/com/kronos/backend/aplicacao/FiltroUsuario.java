package br.com.kronos.backend.aplicacao;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroUsuario {

	private Integer id;
	private LocalDate data;
	private String email;
	private Integer qtdTotal;
	private Integer numeroPagina;
	private String cpf;
	private Integer numeroRegistro;
	private String nome;
	
}
