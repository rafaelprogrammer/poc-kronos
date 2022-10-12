package br.com.kronos.backend.aplicacao.basecurricular.api;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DireitoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private String descricao;
    private Long idNivel;
    private String nivel;
    private String codigo;
    private Boolean ativo;
    private Boolean bncc;
    private Long idInstituicao;  	
    private List<Long> idsCamposExperiencias;
}
