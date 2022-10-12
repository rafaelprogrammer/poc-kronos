package br.com.kronos.backend.aplicacao.api.dto.comum;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
public class PaisDTO  implements Serializable {

    
	private static final long serialVersionUID = 1L;
	
	private Integer id;
    private String nome;
    private String ddi;
    private String gentilico;
    private String sigla;
    
    PaisDTO() {
    }

}
