package br.com.kronos.backend.aplicacao.funcionario;

import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FuncionarioFuncao {
	
	public static final String CACHE_NAME = "funcionarios_funcoes";
    
	private Long id;
    private Long idFuncionario;
    private int idTipoFuncao;
    private boolean funcaoRegistro;
    private boolean ativo;

	@Builder
	public FuncionarioFuncao(Long id, Long idFuncionario, int idTipoFuncao, boolean funcaoRegistro, boolean ativo) {

		super();
        this.id = id;
        this.idFuncionario = exigirNaoNulo(idFuncionario, "Funcionário");
        this.idTipoFuncao = exigirNaoNulo(idTipoFuncao, "Tipo de função");
        this.funcaoRegistro = exigirNaoNulo(funcaoRegistro, "Função de registro"); 
        this.ativo = exigirNaoNulo(ativo, "Ativo");
       
	}
}