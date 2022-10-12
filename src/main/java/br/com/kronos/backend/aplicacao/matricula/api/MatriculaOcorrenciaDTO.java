package br.com.kronos.backend.aplicacao.matricula.api;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MatriculaOcorrenciaDTO implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 1L;
	private long idMatricula;
	private String instituicaoOrigem;
	private String cursoAtual;
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate dataMatricula;
	
}
