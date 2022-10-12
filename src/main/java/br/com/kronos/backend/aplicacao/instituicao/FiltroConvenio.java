package br.com.kronos.backend.aplicacao.instituicao;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroConvenio {

	private Long id;
	private Long idEmpresa;
	private Long idInstituicao;
	private Long idContrato;
	private LocalDate dataContrato;
	private Integer qtdTotal;
	private Integer numeroPagina;
}