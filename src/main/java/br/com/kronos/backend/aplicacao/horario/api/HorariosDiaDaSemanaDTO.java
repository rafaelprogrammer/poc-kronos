package br.com.kronos.backend.aplicacao.horario.api;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class HorariosDiaDaSemanaDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<HoraAtividadeDiaDaSemanaDTO> domingo;
	private List<HoraAtividadeDiaDaSemanaDTO> segunda;
	private List<HoraAtividadeDiaDaSemanaDTO> terca;
	private List<HoraAtividadeDiaDaSemanaDTO> quarta;
	private List<HoraAtividadeDiaDaSemanaDTO> quinta;
	private List<HoraAtividadeDiaDaSemanaDTO> sexta;
	private List<HoraAtividadeDiaDaSemanaDTO> sabado;
	

}
