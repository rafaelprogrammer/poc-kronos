package br.com.kronos.backend.aplicacao.diario;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroDiario{

    private Long id;  
    private Long idAtividade;
    private Integer idTipoPrograma;
	private Integer qtdTotal;
	private Integer numeroPagina;
}