package br.com.kronos.backend.aplicacao.conselho.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ConselhoPessoaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
    private Long idPessoa;
    private Long idConselho;
    private int idTipoFuncao;
    private boolean criacao;
	
}
