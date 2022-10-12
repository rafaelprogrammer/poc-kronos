package br.com.kronos.backend.aplicacao.api.dto.comum;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
public class CidadeDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;
	private String area;
	private String codigoIbge;
	private String codAreaTel;
	private Integer idEstado;
	
	CidadeDTO() {
		
	}

}
