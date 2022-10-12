package br.com.kronos.backend.aplicacao.disciplina.api;

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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DisciplinaResumoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private int codigo;
    private String nome;
    private String sigla;
    private Long idComponente;
    private Long idPeriodo;
    private Long idDisciplinaUnificacao;
    private Long idFuncionario;
    private String nomeFuncionario;
    private boolean encerrado;
    private Integer cargaHorariaPrevista;
    private Integer cargaHorariaMinistrada;
    private Integer numeroDias;
    @JsonFormat(pattern="dd/MM/yyyy")
   	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataAtualizacao; 
}
