package br.com.kronos.backend.aplicacao.funcionario.api;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.kronos.backend.aplicacao.funcionario.EnumTipoPeriodoPonto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AusenteDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idTipoPeriodoPonto;
	private LocalDate dataGeracao;
	private LocalTime horaInicialPrevista;
	private String diaDaSemana;
	private Long idFuncionario;
	@JsonIgnore
	private Long idFuncionarioLogado;
	
	public String getNomeTipoPeriodoPonto() {
		return this.idTipoPeriodoPonto != null ? EnumTipoPeriodoPonto.porId(this.idTipoPeriodoPonto).label() : null;
    }   

}
