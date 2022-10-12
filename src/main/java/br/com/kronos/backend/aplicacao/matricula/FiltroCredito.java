package br.com.kronos.backend.aplicacao.matricula;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroCredito {

    private Long id;     
    private Long idContrato;
    private Long idDisciplina; 
    private Long idTurma;
    private Long idTipoCredito;
	private Integer qtdTotal;
	private Integer numeroPagina;
}