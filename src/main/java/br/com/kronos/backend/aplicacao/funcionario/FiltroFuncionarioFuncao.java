package br.com.kronos.backend.aplicacao.funcionario;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroFuncionarioFuncao {

	private Long id;
    private Long idFuncionario;
    private Long idTipoFuncao;
	private Integer qtdTotal;
	private Integer numeroPagina;
}