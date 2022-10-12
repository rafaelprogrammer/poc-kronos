package br.com.kronos.backend.aplicacao.funcionario;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroFuncionario {

	private Long id;
    private Long idPessoa;
    private Long idInstituicao;
    private Long idTipoFuncao;
    private Long idTipoCategoriaFuncao;
    private Boolean ativo;
    private Integer numeroRegistro;
    private String cpf;
    private String nome;
	private Integer qtdTotal;
	private Integer numeroPagina;
}