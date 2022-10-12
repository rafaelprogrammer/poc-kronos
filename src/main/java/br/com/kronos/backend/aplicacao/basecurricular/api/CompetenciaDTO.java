package br.com.kronos.backend.aplicacao.basecurricular.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CompetenciaDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private String descricao;
    private Long idNivel;
    private String nivel;
    private Long idComponente;
    private String componente;
    private String codigo;
    private Boolean ativo;
    private Boolean bncc;
    private Boolean geral;
    private Long idInstituicao;  	
}
