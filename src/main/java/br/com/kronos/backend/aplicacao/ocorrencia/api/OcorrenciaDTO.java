package br.com.kronos.backend.aplicacao.ocorrencia.api;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import br.com.kronos.backend.adaptadores.serializer.DoubleCommaDeserializer;
import br.com.kronos.backend.adaptadores.serializer.DoubleCommaSerializer;
import br.com.kronos.backend.aplicacao.ocorrencia.EnumFator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OcorrenciaDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    @JsonFormat(pattern="dd/MM/yyyy")
   	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate data;
    @JsonFormat(pattern="HH:mm")
   	@JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime hora;
    private String registro; 
    private Long idTurma;
    private Long idTipoOcorrencia;
    private Long idMatricula;
    private Long idFuncionarioRelator;
    private Long idFuncionarioRegistro;
    @JsonFormat(pattern="dd/MM/yyyy")
   	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataCiencia;
    private Long idResponsavelCiencia;
    private boolean anulado;
    @JsonFormat(pattern="dd/MM/yyyy")
   	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataAnulacao; 
    private String motivoAnulacao;
    private Long idFuncionarioAnulacao;
    private Integer fator;
    @JsonSerialize(using = DoubleCommaSerializer.class)
    @JsonDeserialize(using = DoubleCommaDeserializer.class)
    private Double valor;
    private Long idPessoaSelecionada;
    
    public String getNomeFator() {
    	return this.fator != null ? EnumFator.porValor(this.fator).label() : null;
    }
}

