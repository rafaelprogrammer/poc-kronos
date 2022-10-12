package br.com.kronos.backend.aplicacao.pessoa;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroPessoa {

	private Integer id;
	private LocalDate data;
	private String nome;
	private String cpf;
	private Long idInstituicao;
	private Long idCurso;
	private Integer ano;
	private Long idTurma;
	private Integer qtdTotal;
	private Integer numeroPagina;
	private Long numeroRegistro;
}
