package br.com.kronos.backend.aplicacao.curso.api;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import br.com.kronos.backend.aplicacao.funcionario.EnumTipoFuncao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CursoFuncionarioDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    @JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataInicio;
    @JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataFinal;
    private Long idFuncionario;
    private String nomeFuncionario;
    private Long idCurso;
    private int idTipoFuncao;


	public String getNomeTipoFuncao() {
		return EnumTipoFuncao.porId(this.idTipoFuncao).label();
    }
    	
}
