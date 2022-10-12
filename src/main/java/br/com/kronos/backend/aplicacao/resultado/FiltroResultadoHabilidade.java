package br.com.kronos.backend.aplicacao.resultado;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroResultadoHabilidade{

    private Long id;  
    private Long idAvaliado;
    private Long idAvaliacaoHabilidade;
	private Integer qtdTotal;
	private Integer numeroPagina;
}