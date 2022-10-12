package br.com.kronos.backend.aplicacao.calendario.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DadosCriaSubFaseExecucaoDTO {

	private Long idSubFase;
    private Long idFase;
    private String siglaSubFase;
	
}
