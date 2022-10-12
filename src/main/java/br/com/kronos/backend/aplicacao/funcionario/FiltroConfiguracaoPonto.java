package br.com.kronos.backend.aplicacao.funcionario;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroConfiguracaoPonto {

	private Long id;
    private Long idFuncionario;
    private LocalDate dataInicialVigencia;
    private LocalDate dataFinalVigencia;
	private Integer qtdTotal;
	private Integer numeroPagina;
}