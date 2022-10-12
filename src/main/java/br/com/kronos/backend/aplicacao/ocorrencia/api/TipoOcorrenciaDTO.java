package br.com.kronos.backend.aplicacao.ocorrencia.api;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

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
public class TipoOcorrenciaDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private Integer fator; 
    private String codigo;
    private String descricao;
    @JsonSerialize(using = DoubleCommaSerializer.class)
    @JsonDeserialize(using = DoubleCommaDeserializer.class)
    private Double valor;
    @JsonFormat(pattern="dd/MM/yyyy")
   	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataInicioVigencia;
    @JsonFormat(pattern="dd/MM/yyyy")
   	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataFinalVigencia;
    private Integer qtdOcorrencia;
    private Long idInstituicao;
    
    public String getNomeFator() {
    	return this.fator != null ? EnumFator.porValor(this.fator).label() : null;
    }
 
}

