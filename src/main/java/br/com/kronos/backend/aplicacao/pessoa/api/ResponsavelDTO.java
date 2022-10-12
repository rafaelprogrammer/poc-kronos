package br.com.kronos.backend.aplicacao.pessoa.api;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import br.com.kronos.backend.aplicacao.pessoa.EnumTipoResponsavel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ResponsavelDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataInicio;
	@JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataFim;
    private int prioridade;
    private Long idPessoa;
    private Long idPessoaResponsavel;
    private String nomePessoaResponsavel;
    private Integer idTipoResponsavel;

    public String getNomeTipoResponsavel() {
    	return this.idTipoResponsavel != null ? EnumTipoResponsavel.porId(this.idTipoResponsavel).label() : null;
    }
}