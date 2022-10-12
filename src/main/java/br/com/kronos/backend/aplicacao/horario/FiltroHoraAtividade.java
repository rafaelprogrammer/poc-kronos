package br.com.kronos.backend.aplicacao.horario;

import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroHoraAtividade {

    private Long id;  
    private LocalTime horaInicial;
    private Long idTipoTurno;
    private Long idInstituicao;
	private Integer qtdTotal;
	private Integer numeroPagina;
}