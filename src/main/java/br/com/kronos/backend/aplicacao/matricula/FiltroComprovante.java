package br.com.kronos.backend.aplicacao.matricula;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FiltroComprovante {

    private Long id;     
    private String codigoValidacao;
    private Long idTipoCcomprovante;
    private Long idMatricula;
    private Long idEmpresaRegistro;
	private Integer qtdTotal;
	private Integer numeroPagina;
}