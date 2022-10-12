package br.com.kronos.backend.aplicacao.contrato;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroContrato {

    private Long id;     
    private Integer numero;
    private Integer ano;
    private Long idInstituicao;
    private Long idPeriodoExecucao;
    private Long idMatricula;
    private Long idResponsavelFinanceiro;
    private Boolean dependencia;
	private Integer qtdTotal;
	private Integer numeroPagina;
}