package br.com.kronos.backend.aplicacao.pessoa;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroDocumento {

	private Integer id;
	private Long idPessoa;
	private LocalDate data;
	private Integer numero;
	private Integer idTipo;
	private Integer qtdTotal;
	private Integer numeroPagina;
	
}