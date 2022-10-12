package br.com.kronos.backend.aplicacao.horario.api;

import java.io.Serializable;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import br.com.kronos.backend.aplicacao.curso.EnumTipoTurno;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class HoraAtividadeDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    @JsonFormat(pattern="HH:MM")
   	@JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime horaInicial;
    @JsonFormat(pattern="HH:MM")
   	@JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime horaFinal;
    private int tempoCargaHoraria;
    private int tempoTrabalhoComputado;
    private int idTipoTurno;
    private Long idInstituicao;
    private Integer idTipoMatrizHorario;

    public String getNomeTipoRegimeLetivo() {
		return EnumTipoTurno.porId(this.idTipoTurno).label();
    }	
}

