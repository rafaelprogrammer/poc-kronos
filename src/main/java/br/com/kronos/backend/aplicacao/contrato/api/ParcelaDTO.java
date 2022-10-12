package br.com.kronos.backend.aplicacao.contrato.api;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import br.com.kronos.backend.aplicacao.contrato.EnumTipoFormaPagamento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ParcelaDTO implements Serializable {

    private static final long serialVersionUID = 1L;   
    private long id;
    private Integer numero;
    @JsonFormat(pattern="dd/MM/yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataVencimento;
    private Double valorOriginal;
    @JsonFormat(pattern="dd/MM/yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataPagamento;
    private Double valorJuros;
    private Double valorMulta;
    private Double valorDesconto;
    private Double valorPagamento;
    private String observacao;
    private Integer idTipoFormaPagamento;
    private Long idContrato;
    private Integer numeroParcelas;
    
    public String getNomeTipoFormaPagamento() {
		return EnumTipoFormaPagamento.porId(this.idTipoFormaPagamento).label();
    }
    	
}

