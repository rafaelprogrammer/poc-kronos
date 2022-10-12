package br.com.kronos.backend.aplicacao.funcionario.api;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import br.com.kronos.backend.aplicacao.funcionario.EnumTipoPeriodoPonto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PontoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private boolean tarefaOnline;
	@JsonFormat(pattern="HH:mm")
   	@JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime horaInicialRegistro;
	@JsonFormat(pattern="HH:mm")
   	@JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime horaFinalRegistro;
	@JsonFormat(pattern="HH:mm")
   	@JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime horaInicialInformado;
	@JsonFormat(pattern="HH:mm")
   	@JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime horaFinalInformado;
    @JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate data;
    private Integer idTipoPeriodoPonto;
    private Long idConfiguracaoPonto;
    private Long idFuncionarioLiberacao;
    @JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataLiberacao;
    @JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataHomologacao;
    private Long idFuncionarioHomologacao;
    private String descricao;
    @JsonFormat(pattern="HH:mm")
   	@JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime horaInicialPrevista;
    @JsonFormat(pattern="HH:mm")
   	@JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime horaFinalPrevista;
    private Boolean toleranciaEntrada;
    private Boolean toleranciaSaida;
    @JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataHoje;
    private String diaSemana;
    private String funcionarioLiberacao;
    private String funcionarioHomologacao;
    @JsonIgnore
    private Long idFuncionarioLogado;
    private List<Integer> idsPontos;
    @JsonIgnore
    private boolean libera;
    @JsonIgnore
    private boolean homologa;
    @JsonIgnore
    private boolean cancelaLiberacao;
    @JsonIgnore
    private boolean cancelaHomologacao;
    
    public String getNomeTipoPeriodoPonto() {
		return this.idTipoPeriodoPonto != null ? EnumTipoPeriodoPonto.porId(this.idTipoPeriodoPonto).label() : null;
    }   
	
}
