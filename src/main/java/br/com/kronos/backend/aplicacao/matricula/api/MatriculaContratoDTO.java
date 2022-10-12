package br.com.kronos.backend.aplicacao.matricula.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MatriculaContratoDTO {

	private long id;
	private Long idContrato;
	private String cpfPessoa;
	private Integer numeroRegistroPessoa;
	private String nomePessoa;
	private String nomeTipoSituacaoContrato;
	
}
