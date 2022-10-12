package br.com.kronos.backend.aplicacao.horario;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroSubstituto {

    private Long id;  
    private Long idFuncionario;
    private Long idHorario;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
	private Integer qtdTotal;
	private Integer numeroPagina;
}