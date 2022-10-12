package br.com.kronos.backend.aplicacao.comum;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Builder
@Data
public class Pais  implements Serializable {

    
	private static final long serialVersionUID = 1L;
	
	private Integer id;
    private String nome;
    private String ddi;
    private String gentilico;
    private String sigla;

}
