package br.com.kronos.backend.aplicacao;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroHistoricoPessoa {

	private Long id;
    private Long idPessoa;
    private Long idUsuario;
    private LocalDate data;
	private Integer qtdTotal;
	private Integer numeroPagina;
}