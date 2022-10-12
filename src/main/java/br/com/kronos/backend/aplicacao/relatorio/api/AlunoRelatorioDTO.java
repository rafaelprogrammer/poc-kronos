package br.com.kronos.backend.aplicacao.relatorio.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AlunoRelatorioDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer seq;
	private Integer nr;
	private Integer numeroRegistro;
	private String cpf;
	private String nome;
	private String situacao;
	private String email;
	private String turma;

}
