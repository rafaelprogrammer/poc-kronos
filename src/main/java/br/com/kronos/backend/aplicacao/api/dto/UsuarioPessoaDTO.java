package br.com.kronos.backend.aplicacao.api.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of= {"id", "idPessoa"})
@Data
public class UsuarioPessoaDTO {
	
	private int id;
	private int idPessoa;
	private int numeroRegistro;
	private String cpf;
	private String email;
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate dataNascimento;
	private String nome;
	private boolean ativo;
	private boolean bloqueado;
	
	UsuarioPessoaDTO(){
	}

}
