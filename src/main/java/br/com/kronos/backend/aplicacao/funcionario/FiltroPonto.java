package br.com.kronos.backend.aplicacao.funcionario;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroPonto {

	private Long id;
    private Long idFuncionario;
    private Integer idTipoPeriodoPonto;
    private Integer ano;
    private Integer mes;
    private Integer[] idsDiasSubrimidos;
    private boolean pendente;
	private Integer qtdTotal;
	private Integer numeroPagina;
}