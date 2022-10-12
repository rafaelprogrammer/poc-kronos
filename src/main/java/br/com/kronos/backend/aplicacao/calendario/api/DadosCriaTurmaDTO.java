package br.com.kronos.backend.aplicacao.calendario.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DadosCriaTurmaDTO {

    private Long idPeriodo;
    private Long idPeriodoExecucao;
    private String sigla;
    private Integer idTipoTurno;
	
}
