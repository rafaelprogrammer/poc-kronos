package br.com.kronos.backend.aplicacao.matricula;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroTurmaSala {

    private Long id;     
    private boolean principal;
    private Long idSala;
    private Long idTurma;
	private Integer qtdTotal;
	private Integer numeroPagina;
}