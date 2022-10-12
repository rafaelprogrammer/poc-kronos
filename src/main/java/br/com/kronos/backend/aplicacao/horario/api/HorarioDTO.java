package br.com.kronos.backend.aplicacao.horario.api;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import br.com.kronos.backend.aplicacao.curso.EnumTipoRegimeLetivo;
import br.com.kronos.backend.aplicacao.horario.EnumTipoDiaSemana;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class HorarioDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long idHoraAtividade;
    @JsonFormat(pattern="dd/MM/yyyy")
   	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataInicial;
    @JsonFormat(pattern="dd/MM/yyyy")
   	@JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataFinal;
    @JsonFormat(pattern="HH:mm")
   	@JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime horaInicial;
    @JsonFormat(pattern="HH:mm")
   	@JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime horaFinal;
    private boolean regular;
    private Long idFuncionario;
    private String professor;
    private Long idDisciplina;
    private String disciplina;
    private Long idTurma;
    private int idTipoDiaSemana;
    private int idTipoRegimeLetivo;
    
    public String getNomeTipoRegimeLetivo() {
		return EnumTipoRegimeLetivo.porId(this.idTipoRegimeLetivo).label();
    }
      
    public String getDiaSemana() {
		return EnumTipoDiaSemana.porId(this.idTipoDiaSemana).label();
    }
    	
    	
}

