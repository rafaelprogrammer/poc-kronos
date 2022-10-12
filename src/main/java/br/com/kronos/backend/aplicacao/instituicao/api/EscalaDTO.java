package br.com.kronos.backend.aplicacao.instituicao.api;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import br.com.kronos.backend.aplicacao.avaliacao.EnumTipoAbrangencia;
import br.com.kronos.backend.aplicacao.avaliacao.EnumTipoAvaliacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EscalaDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private String nome;
    @JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataInicialVigencia;
    @JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataFinalVigencia; 
    private Long idInstituicao;
    private int idTipoAbrangencia;  
    private int idTipoAvaliacao;

	public String getNomeTipoAbrangencia() {
		return EnumTipoAbrangencia.porId(this.idTipoAbrangencia).label();
    }

    public String getNomeTipoAvaliacao() {
		return EnumTipoAvaliacao.porId(this.idTipoAbrangencia).label();
    }
    	
}
