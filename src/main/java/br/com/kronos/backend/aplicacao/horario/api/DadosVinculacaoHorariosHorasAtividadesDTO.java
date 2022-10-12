package br.com.kronos.backend.aplicacao.horario.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DadosVinculacaoHorariosHorasAtividadesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HorarioDTO horario;
	private HorariosDiaDaSemanaDTO horariosDiaDaSemana;
	
}
