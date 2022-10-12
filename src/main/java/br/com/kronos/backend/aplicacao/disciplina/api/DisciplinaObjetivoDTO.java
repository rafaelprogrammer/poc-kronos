package br.com.kronos.backend.aplicacao.disciplina.api;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

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
public class DisciplinaObjetivoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private Long idDisciplina;
    private Long idObjetivo;
    private String descricao;
    private String codigo;
    private Boolean ativo;
    private Boolean bncc;
    @JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataInicioVigencia;
    @JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataFinalVigencia;
    private List<Long> idsObjetivos;
    private Long idSubFase;
    private String siglaSubFase;
    	
}
