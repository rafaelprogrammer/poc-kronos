package br.com.kronos.backend.aplicacao.calendario.api;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EventoDTO implements Serializable {

    private static final long serialVersionUID = 1L;   
    private long id;
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dataHoraInicio;
    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dataHoraFinal;
    private boolean diaLetivo;
    private String descricao;
    private Long idCategoriaEvento;
    private String corEvento;
    private Long idCalendario;
    	
}

