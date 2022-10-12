package br.com.kronos.backend.aplicacao.conselho;

import lombok.Builder;
import lombok.Getter;
import static br.com.kronos.backend.aplicacao.validador.Validacoes.exigirNaoNulo;

@Getter
public class ConselhoPessoa {

	private Long idPessoa;
	private Long idConselho;
	private int idTipoFuncao;
	
	@Builder
	public ConselhoPessoa(Long idPessoa, Long idConselho, int idTipoFuncao) {
		
		this.idPessoa = exigirNaoNulo(idPessoa, "Pessoa"); 
		this.idConselho = exigirNaoNulo(idConselho, "Conselho");
		this.idTipoFuncao = exigirNaoNulo(idTipoFuncao, "Tipo de funcao");
	}
	
}
