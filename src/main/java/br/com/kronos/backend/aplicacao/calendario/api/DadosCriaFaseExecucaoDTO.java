package br.com.kronos.backend.aplicacao.calendario.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DadosCriaFaseExecucaoDTO {

    private Long idFase;
    private Long idPeriodo;
    private String siglaFase;
	
}
