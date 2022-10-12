package br.com.kronos.backend.aplicacao.curso.api;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import br.com.kronos.backend.aplicacao.basecurricular.EnumNivel;
import br.com.kronos.backend.aplicacao.curso.EnumTipoRegimeLetivo;
import br.com.kronos.backend.aplicacao.curso.EnumTipoTurno;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CursoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private String nome;
    private String sigla;
    private String tituloMasculino;
    private String tituloFeminino;
    @JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataInicioVigencia;
    @JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataFinalVigencia;
    @JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataLimiteConclusao;
    private int tempoMaximoConclusao;
    private int tempoMaximoDescontinuo;
    private int cargaHoraria;
    private Double notaMinimaAprovacaoDireta;
    private Double notaMinimaAprovacaoRecuperacao;
    private boolean ativo;
    private Integer idNivel;
    private int idTipoRegimeLetivo;
    private int idTipoTurno;
    private Integer idInstituicao;
    private Integer idTipoMatrizHorario;
    private String nomeTipoMatrizHorario;
    
	public String getNomeTipoRegimeLetivo() {
		return EnumTipoRegimeLetivo.porId(this.idTipoRegimeLetivo).label();
    }
    
    public String getNomeTipoTurno() {
		return EnumTipoTurno.porId(this.idTipoTurno).label();
    }
    
    public String getNomeNivel() {
		return EnumNivel.porId(this.idNivel).label();
    }
    	
}
