package br.com.kronos.backend.aplicacao.diario.api;

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
public class AtividadeDisciplinaHabilidadeDTO implements Serializable {

    private static final long serialVersionUID = 1L;    
    private Long idDisciplinaHabilidade;
    private Long idAtividade;
    private Long idHabilidade;
    private String codigoHabilidade;
    private String descricaoHabilidade;
    private List<Long> idsDisciplinaHabilidade;
    private Boolean bncc;
    private String siglaSubFase;
    

}
