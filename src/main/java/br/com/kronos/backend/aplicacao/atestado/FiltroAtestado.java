package br.com.kronos.backend.aplicacao.atestado;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroAtestado {

    private Long id;  
    private Long idPessoa;
    private Long idTipoAtestado;
    private LocalDate dataInicioVigencia;
    private LocalDate dataFinalVigencia;
	private Integer qtdTotal;
	private Integer numeroPagina;
}