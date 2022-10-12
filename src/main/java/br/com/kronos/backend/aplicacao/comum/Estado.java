package br.com.kronos.backend.aplicacao.comum;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Builder
@Data
public class Estado implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String sigla;
	private String nome;
	private String codigoIbge;
	private Integer idPais;

}
