package br.com.kronos.backend.aplicacao.parecer.api;

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
public class ParecerDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long idSubFaseExecucao; 
    private Long idMatricula;
    private Long idTurma;
    @JsonFormat(pattern="dd/MM/yyyy")
   	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate data;
    private Long idFuncionario;
    private StringBuffer texto;
    private boolean rascunho;
    	
    	
}
