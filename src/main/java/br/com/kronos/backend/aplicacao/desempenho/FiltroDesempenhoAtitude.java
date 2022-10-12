package br.com.kronos.backend.aplicacao.desempenho;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroDesempenhoAtitude{

	 private Long idDesempenho;
	 private Long idAtitude;
	 private Integer qtdTotal;
	 private Integer numeroPagina;
}
