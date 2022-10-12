package br.com.kronos.backend.aplicacao;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of= {"id", "idPessoa"})
@Builder
@AllArgsConstructor
@Data
public class UsuarioPessoa {
	
	private int id;
	private int idPessoa;
	private int numeroRegistro;
	private String email;
	private String cpf;
	private LocalDate dataNascimento;
	private String nome;
	private boolean ativo;
	private boolean bloqueado;

}
