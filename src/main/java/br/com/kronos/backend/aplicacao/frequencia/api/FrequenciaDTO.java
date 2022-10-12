package br.com.kronos.backend.aplicacao.frequencia.api;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import br.com.kronos.backend.aplicacao.frequencia.EnumTipoFrequencia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FrequenciaDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    @JsonFormat(pattern="dd/MM/yyyy")
   	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataReposicao; 
    private Long idAtividade;
    private Long idCredito;
    private Long idPessoa;
    private String aluno;
    private Long numeroAtividade;
    private String[] frequencia;
    private String tipoFrequenciaAtestado;
    @JsonIgnore
    private Integer numeroFaltaJustificada;
    @JsonIgnore
    private Integer numeroPresenca;
    @JsonIgnore
    private Integer numeroFalta;
    @JsonIgnore
    private EnumTipoFrequencia tipo;
    @JsonIgnore
    private boolean criarTipoTP;
    @JsonIgnore
    private boolean criarTipoTF;
    @JsonIgnore
    private boolean criarTipoAD;
    @JsonIgnore
    private boolean limpar;
    @JsonIgnore
    private boolean registrarReposicao;
    @JsonIgnore
    private boolean anularReposicao;
    @JsonIgnore
    private boolean atualizaIndividual;
    @JsonIgnore
    private boolean tipoTFIndividual;
}

