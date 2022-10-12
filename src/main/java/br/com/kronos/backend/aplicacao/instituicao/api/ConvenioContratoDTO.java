package br.com.kronos.backend.aplicacao.instituicao.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ConvenioContratoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private String cnpj;
    private String empresa;
    private String tipoDesconto;
    private Double percentualDesconto;
    private Long idEmpresa;
    private Long idInstitucicao;
    
}
