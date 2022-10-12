package br.com.kronos.backend.aplicacao.horario.api;

import java.io.Serializable;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import lombok.Builder;
import lombok.Data;

@Data
public class HoraAtividadeDiaDaSemanaDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idHorario;
	private Long idAtividade;
	@JsonFormat(pattern="HH:mm")
   	@JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
	private LocalTime horaInicial;
	@JsonFormat(pattern="HH:mm")
   	@JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
	private LocalTime horaFinal;
	private boolean bloqueado;
	private boolean selecionado;
	private String situacao;
	
	HoraAtividadeDiaDaSemanaDTO() {
	}
	 
	@Builder
	public HoraAtividadeDiaDaSemanaDTO(Long idHorario, Long idAtividade, LocalTime horaInicial, LocalTime horaFinal,
			boolean editavel, boolean turma, String situacao) {
		super();
		this.idHorario = idHorario;
		this.idAtividade = idAtividade;
		this.horaInicial = horaInicial;
		this.horaFinal = horaFinal;
		if (editavel && turma) {
			if (idHorario == null) {
				this.bloqueado = false;
				this.selecionado = false;
			} else {
				this.bloqueado = false;
				this.selecionado = true;
			}
		}
		if (!editavel && turma || (!editavel && !turma)) {
			this.bloqueado = true;
			this.selecionado = true;
		}
		this.situacao = situacao;
	}

	
}
