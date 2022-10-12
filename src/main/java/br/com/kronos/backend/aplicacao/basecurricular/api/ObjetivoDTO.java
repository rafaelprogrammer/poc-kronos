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
public class ObjetivoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private Long idCampoExperiencia;
    private String camposExperiencia;
    private String descricao;
    private String codigo;
    private Boolean ativo;
    private Boolean bncc;
    private Long idInstituicao;
    private Long idFaixaAno;
    private String faixaAno;
}
