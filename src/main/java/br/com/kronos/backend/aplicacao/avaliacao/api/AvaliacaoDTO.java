package br.com.kronos.backend.aplicacao.avaliacao.api;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AvaliacaoDTO implements Serializable {

    private static final long serialVersionUID = 1L;  
    private long id;
    @JsonFormat(pattern="HH:mm")
   	@JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime horaInicio;
    private boolean grupo;
    private Integer numeroMaxParticipante;
    private Integer peso;
    private String observacao;
    private boolean anulada;
    private String motivoAnulacao;
    private Integer idTipoFormato;
    private String nomeTipoFormato;
    private Integer idTipoRegistroNota;
    private Integer idTipoAbrangencia;
    private String nomeTipoAbrangencia;
    private Long idAtividade;
    private Integer idTipoAvaliacao;
    private String nomeTipoAvaliacao;
    private Integer tempoDuracao;
    private Integer qtdAvaliados;
    private Long idTurma;
    private Long idDisciplina;
    @JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataPrevista;
    private List<GrupoAvaliacaoDTO> gruposAvaliacoes;
    private List<AvaliacaoHabilidadeDTO> avaliacoesHabilidades;
    	
}

