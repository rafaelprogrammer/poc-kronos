package br.com.kronos.backend.aplicacao.matricula.api;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import br.com.kronos.backend.aplicacao.matricula.EnumTipoResultado;
import br.com.kronos.backend.aplicacao.matricula.EnumTipoSituacaoMatricula;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MatriculaDTO implements Serializable {

    private static final long serialVersionUID = 1L;  
    private long id;
    @JsonFormat(pattern="dd/MM/yyyy")
   	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate data;
    private Integer anoInicioCurso;
    private Integer semestreInicioCurso;
    @JsonFormat(pattern="dd/MM/yyyy")
   	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataInicioCurso;
    private Integer anoConclusaoCurso;
    private Integer semestreConclusaoCurso;
    @JsonFormat(pattern="dd/MM/yyyy")
   	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataConclusaoCurso;
    @JsonFormat(pattern="dd/MM/yyyy")
   	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataColacaoGrau;
    private Long idPessoa;
    private Integer numeroRegistroPessoa;
    private String cpfPessoa;
    private String nomePessoa;
    @JsonFormat(pattern="dd/MM/yyyy")
   	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataNascimentoPessoa;
    private Integer idGeneroPessoa;
    private Integer idArqAnexoPessoa;
    private Long idCurso;
    private String nomeCurso;
    private Integer idTipoSituacaoMatricula;
    private Integer idTipoResultado;
    private Long idEmpresaOrigem;
    private String nomeInstituicaoOrigem;

	public String getNomeTipoSituacaoMatricula() {
		return EnumTipoSituacaoMatricula.porId(this.idTipoSituacaoMatricula).label();
    }
	
	public String getNomeTipoResultado() {
		EnumTipoResultado enumTipoResultado = EnumTipoResultado.porId(this.idTipoResultado);
		return enumTipoResultado !=null ? enumTipoResultado.label() : null;
    }

    
}

