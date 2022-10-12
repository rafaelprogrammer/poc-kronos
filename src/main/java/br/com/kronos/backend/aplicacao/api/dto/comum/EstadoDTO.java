package br.com.kronos.backend.aplicacao.api.dto.comum;

import java.io.Serializable;

import lombok.Data;

@Data
public class EstadoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String sigla;
	private String nome;
	private String codigoIbge;
	private Integer idPais;
	
	EstadoDTO() {
	}
}
