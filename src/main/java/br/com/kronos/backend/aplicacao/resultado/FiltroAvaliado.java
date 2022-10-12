package br.com.kronos.backend.aplicacao.resultado;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroAvaliado{

    private Long id;  
    private Long idAvaliacao;
    private Long idCredito;
    private Long idGrupoAvaliacao;
    private Long idTurma;
    private LocalDate dataAvaliacao;
    private Long idDisciplina;
    private Integer anoTurma;
	private Integer qtdTotal;
	private Integer numeroPagina;
}