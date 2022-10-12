package br.com.kronos.backend.aplicacao.ocorrencia;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroTipoOcorrencia{

    private Long id;  
    private Integer fator;
    private Integer idInstituicaoUsuarioLogado;
    private String vigente;
    private String codigo;
    private String descricao; 
	private Integer qtdTotal;
	private Integer numeroPagina;
}