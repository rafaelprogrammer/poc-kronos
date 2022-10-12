package br.com.kronos.backend.aplicacao.api.dto.comum;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MesDTO  implements Serializable {

    
	private static final long serialVersionUID = 1L;
	
	private Integer numero;
	private String nome;
    
}
