package br.com.kronos.backend.aplicacao.basecurricular.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FaixaAnoDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String nome;
    private Integer idNivel;
    private String nivel;

}