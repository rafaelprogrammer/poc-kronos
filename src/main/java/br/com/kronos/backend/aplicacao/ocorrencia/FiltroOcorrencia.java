package br.com.kronos.backend.aplicacao.ocorrencia;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroOcorrencia{

    private Long id;  
    private Long idTurma;
    private Long idTipoOcorrencia;
    private Long idMatricula;
    private Long idPessoa;
	private Integer qtdTotal;
	private Integer numeroPagina;
}