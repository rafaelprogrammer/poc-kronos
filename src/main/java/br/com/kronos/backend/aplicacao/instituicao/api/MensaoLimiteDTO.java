package br.com.kronos.backend.aplicacao.instituicao.api;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of= {"idLimite", "idMensao"})
@Builder
@Data
public class MensaoLimiteDTO {
	
	 private Long idLimite;
	 private Long idMensao;
	 private String nome;
	 private String simbolo;
	 private Double maximo;
	 private Double minimo;

}
