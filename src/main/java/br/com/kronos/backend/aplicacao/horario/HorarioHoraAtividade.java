package br.com.kronos.backend.aplicacao.horario;

import lombok.Builder;
import lombok.Getter;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

@Getter
public class HorarioHoraAtividade {

	private Long idHorario;
	private Long idHoraAtividade;
	
	@Builder
	public HorarioHoraAtividade(Long idHorario, Long idHoraAtividade) {
		
		this.idHorario = exigirNaoNulo(idHorario, "Horário"); 
		this.idHoraAtividade = exigirNaoNulo(idHoraAtividade, "Hora atividade");
	}
	
}
