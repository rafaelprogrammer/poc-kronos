package br.com.kronos.backend.aplicacao.disciplina.api;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import br.com.kronos.backend.aplicacao.curso.EnumTipoRegimeLetivo;
import br.com.kronos.backend.aplicacao.disciplina.EnumTipoDisciplina;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DisciplinaDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private int codigo;
    private String nome;
    private String sigla;
    private Integer cargaHorariaTotal;
    private Integer cargaHorariaPresencial;
    private Integer cargaHorariaDistancia;
    private boolean ativa;
    private boolean obrigatoria;
    private Double valor;
    private Integer idTipoDisciplina;
    private Integer idTipoRegimeLetivo;
    private Long idPeriodo;
    private String siglaPeriodo;
    private Long idComponente;
    private boolean preRequisitos;
    @JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataInicioVigencia;
    @JsonFormat(pattern="dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataFinalVigencia;
    private Long idDisciplinaUnificacao;
    private String siglaDisciplinaUnificacao;

	public String getNomeTipoDisciplina() {
		return EnumTipoDisciplina.porId(this.idTipoDisciplina).label();
    }

    public String getNomeTipoRegimeLetivo() {
		return EnumTipoRegimeLetivo.porId(this.idTipoRegimeLetivo).label();
    }
    	
}
