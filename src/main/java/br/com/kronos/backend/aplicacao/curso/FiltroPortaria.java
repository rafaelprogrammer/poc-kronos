package br.com.kronos.backend.aplicacao.curso;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroPortaria {

	private Long id;  
    private String descricao;
	private int idTipoPortaria;
	private Long idCurso; 
	private Integer qtdTotal;
	private Integer numeroPagina;
}