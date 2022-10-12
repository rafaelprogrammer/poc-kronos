package br.com.kronos.backend.aplicacao.diario.api;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DiarioDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private String procedimento;
    private String recurso;
    private String observacao;
    private Long idAtividade;
    private Integer idTipoPrograma;
    private String nomePrograma;

}

